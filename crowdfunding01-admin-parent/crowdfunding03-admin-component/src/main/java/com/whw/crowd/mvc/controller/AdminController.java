package com.whw.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.whw.crowd.entity.Admin;
import com.whw.crowd.service.api.AdminService;
import com.whw.crowd.util.CrowdConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping("admin/to/save")
    public String AdminAdd(Admin admin){
        adminService.saveAdmin(admin);
        return "redirect:/admin/get/page?pageNum="+Integer.MAX_VALUE;
    }

    @RequestMapping({"/admin/remove/{adminId}/{pageNum}/{keyword}","/admin/remove/{adminId}/{pageNum}","/admin/remove/{adminId}"})
    public String AdminRemove(@PathVariable("adminId") Integer id,
                              @PathVariable(value = "pageNum",required = false) String pageNum,
                              @PathVariable(value = "keyword",required = false) String keyword,
                              HttpSession session){
        Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTER_NAME_LOGIN_ADMIN);

        if (admin.getId() == id){
            throw new RuntimeException(CrowdConstant.DELETE_ERROR);
        }

        int updateNum = adminService.removeAdmin(id);
        if (keyword == null){
            if (pageNum == null){
                return "redirect:/admin/get/page";
            }
            return "redirect:/admin/get/page?pageNum="+pageNum;
        }
        return "redirect:/admin/get/page?pageNum="+pageNum+"&keyword="+keyword;
    }


    @RequestMapping("/admin/get/page")
    public String getPageInfo(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                              @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                              @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                              Model model) {


        // 调用Service方法获取PageInfo对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);

        logger.info(pageInfo.getTotal() + "----------------------------------");
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
