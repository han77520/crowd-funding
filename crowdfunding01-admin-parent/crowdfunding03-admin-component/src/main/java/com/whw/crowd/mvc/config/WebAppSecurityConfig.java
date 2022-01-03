package com.whw.crowd.mvc.config;

import com.whw.crowd.util.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 王瀚文
 * @Description:
 * @date 2022-01-02 12:06
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用全局方法权限控制功能
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
//        builder
//                .inMemoryAuthentication()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("han77")
//                .password(new BCryptPasswordEncoder().encode("172161"))
//                .roles("ADMIN");

        // 基于数据库的登录
        builder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .authorizeRequests()
                .antMatchers("/admin/login")
                .permitAll()
                .antMatchers("/bootstrap/**", "/crowd/**", "/css/**", "/fonts/**", "/img/**", "/jquery/**", "/layer/**", "/script/**", "/WEB-INF/**", "/ztree/**")
                .permitAll()
                .antMatchers("/admin/get/page")  // 针对 分页显示Admin数据设置角色
                .access("hasRole('经理') or hasAnyAuthority('user:get')") // 具备经理角色才可以访问
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable()
                .formLogin()
                .loginPage("/admin/login")
                .loginProcessingUrl("/security/do/login")
                .defaultSuccessUrl("/admin/to/main")
                .usernameParameter("loginAcct")
                .passwordParameter("userPswd")
                .permitAll() // 这个必须有
                .and().exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {

            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                request.setAttribute("exception", new Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
                request.getRequestDispatcher("/WEB-INF/templates/system-errors.jsp").forward(request, response);
            }
        })
                .and()
                .logout()
                .logoutUrl("/security/do/logout")
                .logoutSuccessUrl("/admin/login");
    }

}
