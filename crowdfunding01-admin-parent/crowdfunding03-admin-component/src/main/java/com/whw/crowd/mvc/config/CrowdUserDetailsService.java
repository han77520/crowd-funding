package com.whw.crowd.mvc.config;

import com.whw.crowd.entity.Admin;
import com.whw.crowd.entity.Role;
import com.whw.crowd.service.api.AdminService;
import com.whw.crowd.service.api.AuthService;
import com.whw.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-03 10:17
 */
@Component
public class CrowdUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    // 总目标：根据表单提交的用户名查询 User 对象，并装配角色、权限等信息
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1.根据账号查询Admin对象
        Admin admin = adminService.getAdminByLoginAcct(username);

        // 2.给 Admin 设置角色权限信息
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 3.根据adminId 获取角色信息
        List<Role> roleNameList = roleService.getAssignedRole(admin.getId());

        // 4.根据adminId 获取权限信息
        List<String> authoritiesNameList = authService.getAssignedAuthNameByAdminId(admin.getId());

        String roleName = "";
       if (roleNameList != null && roleNameList.size() > 0) {
           for (Role role : roleNameList) {
               // 将角色的name前面加“ROLE_”
               roleName = "ROLE_" + role.getName();
               authorities.add(new SimpleGrantedAuthority(roleName));
           }
       }

        if (null != authoritiesNameList && authoritiesNameList.size() > 0) {
            for (String authName : authoritiesNameList) {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authName);
                authorities.add(simpleGrantedAuthority);
            }
        }

        SecurityAdmin securityAdmin = new SecurityAdmin(admin, authorities);

        return securityAdmin;
    }
}
