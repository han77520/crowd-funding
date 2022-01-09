package com.whw.crowd.controller;

import com.whw.crowd.entity.vo.DetailProjectVO;
import com.whw.crowd.entity.vo.OrderProjectVO;
import com.whw.crowd.entity.vo.PortalTypeVO;
import com.whw.crowd.entity.vo.ProjectVO;
import com.whw.crowd.service.api.ProjectService;
import com.whw.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-07 21:21
 */
@RestController
public class ProjectProviderController {

    @Autowired
    private ProjectService projectService;


    @RequestMapping("/get/project/detail/remote/{projectId}")
    public ResultEntity<DetailProjectVO> getDetailProjectVORemote(@PathVariable("projectId") Integer projectId) {

        DetailProjectVO detailProjectVO = null;
        try {
            detailProjectVO = projectService.getDetailProjectVO(projectId);
            return ResultEntity.successWithData(detailProjectVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }


    @RequestMapping("/get/portal/type/project/data")
    public ResultEntity<List<PortalTypeVO>> getPortalTypeProjectDataRemote() {

        try {
            List<PortalTypeVO> portalTypeVO = projectService.getPortalTypeVO();

            return ResultEntity.successWithData(portalTypeVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

    }

    @RequestMapping("/save/projectVO/remote")
    ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId) {

        try {
            projectService.saveProject(projectVO, memberId);

            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }


}
