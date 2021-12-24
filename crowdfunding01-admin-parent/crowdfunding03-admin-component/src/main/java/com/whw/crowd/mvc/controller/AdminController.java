package com.whw.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.whw.crowd.entity.Admin;
import com.whw.crowd.service.api.AdminService;
import com.whw.crowd.util.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 王瀚文
 * @Description:
 * @date 2021-12-20 19:28
 */
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;


    @RequestMapping("/admin/get/page")
    public String getPageInfo(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                              @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                              @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                              Model model) {

        // 调用Service方法获取PageInfo对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);

        model.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO,pageInfo);
        return "admin_page";
    }

    @RequestMapping("/admin/do/login")
    public String doLogin(@RequestParam("loginAcct") String loginAcct,
                          @RequestParam("userPswd") String userPswd,
                          HttpSession session){

        Admin admin = adminService.getAdminByLoginAcct(loginAcct,userPswd);

        session.setAttribute(CrowdConstant.ATTER_NAME_LOGIN_ADMIN,admin);

        return "redirect:/admin/to/main";
    }

    @RequestMapping("/admin/do/logout")
    public String logout( HttpSession session){
        session.invalidate();

        return "redirect:/";
    }
}
