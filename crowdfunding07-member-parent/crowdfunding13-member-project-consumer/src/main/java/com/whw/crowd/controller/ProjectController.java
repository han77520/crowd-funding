package com.whw.crowd.controller;

import com.whw.crowd.api.MySQLRemoteService;
import com.whw.crowd.config.OSSProperties;
import com.whw.crowd.entity.vo.*;
import com.whw.crowd.util.CrowdConstant;
import com.whw.crowd.util.CrowdUtil;
import com.whw.crowd.util.ResultEntity;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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


    @RequestMapping("/get/project/detail/{projectId}")
    public String getProjectDetail(@PathVariable("projectId") Integer projectId,ModelMap modelMap) {

        ResultEntity<DetailProjectVO> resultEntity = mySQLRemoteService.getDetailProjectVORemote(projectId);

        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            DetailProjectVO detailProjectVO = resultEntity.getData();

            modelMap.addAttribute("detailProjectVO",detailProjectVO);
        }

        return "project_detail";
    }

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
            // 1.???session???????????????????????????ProjectVO??????
            ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

            // 2.??????projectVO?????????null
            if(projectVO == null) {
                return ResultEntity.failed(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
            }

            // 3.???projectVO??????????????????????????????????????????
            List<ReturnVO> returnVOList = projectVO.getReturnVOList();

            // 4.??????returnVOList??????????????????
            if(returnVOList == null || returnVOList.size() == 0) {

                // 5.?????????????????????returnVOList???????????????
                returnVOList = new ArrayList<>();
                // 6.?????????????????????????????????????????????????????????projectVO?????????
                projectVO.setReturnVOList(returnVOList);
            }

            // 7.???????????????????????????returnVO??????????????????
            returnVOList.add(returnVO);

            // 8.?????????????????????ProjectVO??????????????????Session?????????????????????????????????????????????Redis
            session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);

            // 9.????????????????????????????????????
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

        // ????????????
        ResultEntity<String> uploadFileToOss = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(), ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                returnPicture.getInputStream(),
                ossProperties.getBucketName(), ossProperties.getBucketDomain(),
                returnPicture.getOriginalFilename());


        return uploadFileToOss;
    }


    /**
     * @param projectVO         ????????????????????????????????????????????????
     * @param headerPicture     ?????????????????????
     * @param detailPictureList ???????????????????????????
     * @param session           ?????????????????????????????????ProjectVO???????????????session(redis)???
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
        // ????????????
        ResultEntity<String> uploadFileToOss = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(), ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                headerPicture.getInputStream(),
                ossProperties.getBucketName(), ossProperties.getBucketDomain(),
                headerPicture.getOriginalFilename());

        String result = uploadFileToOss.getResult();

        if (ResultEntity.SUCCESS.equals(result)) {

            // ???????????????????????????
            String headerPicturePath = uploadFileToOss.getData();

            projectVO.setHeaderPicturePath(headerPicturePath);
        }

        if (null == detailPictureList || detailPictureList.size() == 0) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
            return "project_launch";
        }

        List<String> detailPicturePaths = new ArrayList<>();

        // ??????detailPictureList??????
        for (MultipartFile detailPicture : detailPictureList) {

            if (detailPicture.isEmpty()) {
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
                return "project_launch";
            }

            // ????????????
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
