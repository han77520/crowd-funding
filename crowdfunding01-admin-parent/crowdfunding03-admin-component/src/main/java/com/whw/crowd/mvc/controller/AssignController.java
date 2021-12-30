package com.whw.crowd.mvc.controller;

import com.whw.crowd.entity.Role;
import com.whw.crowd.service.api.AdminService;
import com.whw.crowd.service.api.RoleService;
import com.whw.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 王瀚文
 * @Description:
 * @date 2021-12-29 21:23
 */
@Controller
public class AssignController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("assign/do/role")
    public String s(){
        return null;
    }

    @RequestMapping({"/assign/to/assign/role/{adminId}/{pageNum}/{keyword}", "/assign/to/assign/role/{adminId}/{pageNum}", "/assign/to/assign/role/{adminId}"})
    public String toAssignRolePage(@PathVariable("adminId") Integer id,
                                   @PathVariable(value = "pageNum", required = false) String pageNum,
                                   @PathVariable(value = "keyword", required = false) String keyword,
                                   ModelMap modelMap
                                   ){
        // 查询已分配的角色
        List<Role> assignedRoleList = roleService.getAssignedRole(id);

        // 查询未分配的角色
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(id);

        // 存入模型
        modelMap.addAttribute("adminId",id);
        modelMap.addAttribute("pageNum",pageNum);
        modelMap.addAttribute("keyword",keyword);
        modelMap.addAttribute("assignedRoleList",assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList",unAssignedRoleList);

        return "assign_role";
    }

}
