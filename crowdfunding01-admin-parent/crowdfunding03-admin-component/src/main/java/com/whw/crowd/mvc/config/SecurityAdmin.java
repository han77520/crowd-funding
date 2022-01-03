package com.whw.crowd.mvc.config;

import com.whw.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * @author 王瀚文
 * @Description: 考虑到User对象中仅仅包含账号和密码，为的是能获取到原始的Admin对象
 * @date 2022-01-03 9:59
 */
public class SecurityAdmin extends User {

    private Admin originalAdmin;

    public SecurityAdmin(Admin originalAdmin, List<GrantedAuthority> authorities) {
        super(originalAdmin.getLoginAcct(),originalAdmin.getUserPswd(),authorities);

        this.originalAdmin = originalAdmin;

        this.originalAdmin.setUserPswd(null);
    }

    public Admin getOriginalAdmin(){
        return originalAdmin;
    }



}
