package com.whw.crowd.mvc.controller;

import checkers.units.quals.A;
import com.whw.crowd.entity.Auth;
import com.whw.crowd.entity.Role;
import com.whw.crowd.service.api.AdminService;
import com.whw.crowd.service.api.AuthService;
import com.whw.crowd.service.api.RoleService;
import com.whw.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 王瀚文
 * @Description:
 * @date 2021-12-29 21:23
 */
@Controller
public class AssignController {


    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @ResponseBody
    @RequestMapping("/assign/get/assigned/authId")
    public ResultEntity<List<Integer>> getAssignedAuthId(@RequestParam("roleId") Integer roleId){

        List<Integer> authIdList = authService.getAssignedAuthId(roleId);

        return ResultEntity.successWithData(authIdList);
    }

    @ResponseBody
    @RequestMapping("/assign/get/all/auth")
    public ResultEntity<List<Auth>> getAllAuth() {

        List<Auth> authList = authService.getAll();

        return ResultEntity.successWithData(authList);
    }

    @RequestMapping("assign/do/role")
    public String saveAdminRoleRelationship(@RequestParam("adminId") Integer adminId,
                                            @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                                            @RequestParam(value = "keyword", required = false) String keyword,
                                            @RequestParam(value = "roleIdList", required = false) List<Integer> roleList) {
        roleService.saveAdminRoleRelationship(adminId, roleList);

        if (keyword == null) {
            if (pageNum == null) {
                return "redirect:/admin/get/page";
            }
            return "redirect:/admin/get/page?pageNum=" + pageNum;
        }
        return "redirect:/admin/get/page?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @RequestMapping({"/assign/to/assign/role/{adminId}/{pageNum}/{keyword}", "/assign/to/assign/role/{adminId}/{pageNum}", "/assign/to/assign/role/{adminId}"})
    public String toAssignRolePage(@PathVariable("adminId") Integer id,
                                   @PathVariable(value = "pageNum", required = false) String pageNum,
                                   @PathVariable(value = "keyword", required = false) String keyword,
                                   ModelMap modelMap
    ) {
        // 查询已分配的角色
        List<Role> assignedRoleList = roleService.getAssignedRole(id);

        // 查询未分配的角色
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(id);

        // 存入模型
        modelMap.addAttribute("adminId", id);
        modelMap.addAttribute("pageNum", pageNum);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("assignedRoleList", assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);

        return "assign_role";
    }

}
