package com.whw.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.whw.crowd.entity.Admin;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王瀚文
 * @Description:
 * @date 2021-12-18 19:00
 */
public interface AdminService {

    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    PageInfo<Admin> getPageInfo(String keyword,Integer pageNum,Integer pageSize);

}
