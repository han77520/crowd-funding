package com.whw.crowd.controller;

import com.whw.crowd.api.MySQLRemoteService;
import com.whw.crowd.entity.vo.PortalTypeVO;
import com.whw.crowd.util.CrowdConstant;
import com.whw.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-05 12:56
 */
@Controller
public class PortalController {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/")
    public String showPortalPage(ModelMap modelMap) {

        ResultEntity<List<PortalTypeVO>> resultEntity = mySQLRemoteService.getPortalTypeProjectDataRemote();

        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())){
            List<PortalTypeVO> portalTypeVOS = resultEntity.getData();

            modelMap.addAttribute(CrowdConstant.ATTR_NAME_PORTAL,portalTypeVOS);
        }

        return "portal";
    }
}
