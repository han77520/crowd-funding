package com.whw.crowd.controller;

import com.whw.crowd.api.MySQLRemoteService;
import com.whw.crowd.config.OSSProperties;
import com.whw.crowd.entity.vo.MemberConfirmInfoVO;
import com.whw.crowd.entity.vo.MemberLoginVO;
import com.whw.crowd.entity.vo.ProjectVO;
import com.whw.crowd.entity.vo.ReturnVO;
import com.whw.crowd.util.CrowdConstant;
import com.whw.crowd.util.CrowdUtil;
import com.whw.crowd.util.ResultEntity;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-07 22:51
 */
@Controller
public class ProjectController {

    @Autowired
    private OSSProperties ossProperties;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/create/confirm")
    public String saveConfirm(ModelMap modelMap,
                              HttpSession session,
                              MemberConfirmInfoVO memberConfirmInfoVO) {

        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

        if (null == projectVO) {
            throw new RuntimeException(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
        }

        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);

        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTER_NAME_LOGIN_MEMBER);


        ResultEntity<String> saveProjectVORemote = mySQLRemoteService.saveProjectVORemote(projectVO, memberLoginVO.getId());

        if (ResultEntity.FAILED.equals(saveProjectVORemote.getResult())) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveProjectVORemote.getMessage());
            return "project_confirm";
        }

        session.removeAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

        return "redirect:http://www.crowd.com/project/create/success";
    }


    @ResponseBody
    @RequestMapping("/create/save/return")
    public ResultEntity<String> saveReturn(ReturnVO returnVO, HttpSession session) {

        try {
            // 1.从session域中读取之前缓存的ProjectVO对象
            ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

            // 2.判断projectVO是否为null
            if(projectVO == null) {
                return ResultEntity.failed(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
            }

            // 3.从projectVO对象中获取存储回报信息的集合
            List<ReturnVO> returnVOList = projectVO.getReturnVOList();

            // 4.判断returnVOList集合是否有效
            if(returnVOList == null || returnVOList.size() == 0) {

                // 5.创建集合对象对returnVOList进行初始化
                returnVOList = new ArrayList<>();
                // 6.为了让以后能够正常使用这个集合，设置到projectVO对象中
                projectVO.setReturnVOList(returnVOList);
            }

            // 7.将收集了表单数据的returnVO对象存入集合
            returnVOList.add(returnVO);

            // 8.把数据有变化的ProjectVO对象重新存入Session域，以确保新的数据最终能够存入Redis
            session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);

            // 9.所有操作成功完成返回成功
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed(e.getMessage());
        }
    }


    @SneakyThrows
    @ResponseBody
    @RequestMapping("/create/upload/return/picture")
    public ResultEntity<String> createUploadReturnPicture(@RequestParam("returnPicture") MultipartFile returnPicture) {

        // 执行上传
        ResultEntity<String> uploadFileToOss = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(), ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                returnPicture.getInputStream(),
                ossProperties.getBucketName(), ossProperties.getBucketDomain(),
                returnPicture.getOriginalFilename());


        return uploadFileToOss;
    }


    /**
     * @param projectVO         接收处理上传图片你之外的普通数据
     * @param headerPicture     接收上传的头图
     * @param detailPictureList 接收上传的详情图片
     * @param session           用于将收集了部分数据的ProjectVO对象存储到session(redis)中
     * @return
     */
    @SneakyThrows
    @RequestMapping("/create/project/information")
    public String saveProjectBasicInfo(ProjectVO projectVO,
                                       MultipartFile headerPicture,
                                       List<MultipartFile> detailPictureList,
                                       HttpSession session,
                                       ModelMap modelMap) {

        boolean empty = headerPicture.isEmpty();
        if (empty) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_EMPTY);
            return "project_launch";
        }
        // 执行上传
        ResultEntity<String> uploadFileToOss = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(), ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                headerPicture.getInputStream(),
                ossProperties.getBucketName(), ossProperties.getBucketDomain(),
                headerPicture.getOriginalFilename());

        String result = uploadFileToOss.getResult();

        if (ResultEntity.SUCCESS.equals(result)) {

            // 获取文件存储的路径
            String headerPicturePath = uploadFileToOss.getData();

            projectVO.setHeaderPicturePath(headerPicturePath);
        }

        if (null == detailPictureList || detailPictureList.size() == 0) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
            return "project_launch";
        }

        List<String> detailPicturePaths = new ArrayList<>();

        // 遍历detailPictureList集合
        for (MultipartFile detailPicture : detailPictureList) {

            if (detailPicture.isEmpty()) {
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
                return "project_launch";
            }

            // 执行上传
            ResultEntity<String> detailUploadResultEntity = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(), ossProperties.getAccessKeyId(),
                    ossProperties.getAccessKeySecret(),
                    detailPicture.getInputStream(),
                    ossProperties.getBucketName(), ossProperties.getBucketDomain(),
                    detailPicture.getOriginalFilename());

            String detailUploadResult = detailUploadResultEntity.getResult();

            if (ResultEntity.SUCCESS.equals(detailUploadResult)) {
                String detailPicturePath = detailUploadResultEntity.getData();

                detailPicturePaths.add(detailPicturePath);
            } else {
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);
                return "project_launch";
            }
        }
        projectVO.setDetailPicturePathList(detailPicturePaths);


        session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);

        return "redirect:http://www.crowd.com/project/return/info/page";
    }
}
