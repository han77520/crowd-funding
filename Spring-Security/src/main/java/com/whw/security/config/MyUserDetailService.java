package com.whw.security.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author 王瀚文
 * @Description:
 * @date 2022-01-02 9:33
 */
@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据表单提交的用户名称查询User对象，并装配角色、权限等信息
     * @param username 表单提交的用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String sql = "";

//        jdbcTemplate.query(sql,);

        return null;
    }
}
