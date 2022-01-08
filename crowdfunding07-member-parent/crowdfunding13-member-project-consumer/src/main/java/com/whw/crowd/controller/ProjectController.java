package com.whw.crowd.controller;

import com.whw.crowd.config.OSSProperties;
import com.whw.crowd.entity.vo.ProjectVO;
import com.whw.crowd.util.CrowdConstant;
import com.whw.crowd.util.CrowdUtil;
import com.whw.crowd.util.ResultEntity;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
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
            }else {
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);
                return "project_launch";
            }
        }
        projectVO.setDetailPicturePathList(detailPicturePaths);


        session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT,projectVO);

        return "redirect:http://www.crowd.com/project/return/info/page";
    }
}
