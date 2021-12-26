package com.whw.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.whw.crowd.entity.Role;
import com.whw.crowd.service.api.RoleService;
import com.whw.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 王瀚文
 * @Description:
 * @date 2021-12-25 19:41
 */
@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ResponseBody
    @RequestMapping("/role/save")
    public ResultEntity<String> roleSave(Role role){
        roleService.saveRole(role);
        return ResultEntity.successWithoutData();
    }


    @ResponseBody
    @RequestMapping("/role/get/page")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "") String keyword
    ) {

        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
        return ResultEntity.successWithData(pageInfo);

    }
}
