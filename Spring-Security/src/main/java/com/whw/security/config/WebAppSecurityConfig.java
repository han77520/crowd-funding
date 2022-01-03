package com.whw.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author 王瀚文
 * @Description:
 * @date 2022-01-01 17:03
 */
@Configuration
@EnableWebSecurity // 启用web环境下权限控制的功能
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication()
                .withUser("han77")
                .password("172161")
                .roles("ADMIN", "学徒")
                .and()
                .withUser("jerry")
                .password("111111")
                .authorities("UPDATE");
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .authorizeRequests() // 对请求进行授权
                .antMatchers("/index.jsp") // 针对index.jsp路径进行授权
                .permitAll()  // 可以无条件访问
                .antMatchers("/layui/**")  // 针对layui目录下的所有资源进行授权
                .permitAll()
                .antMatchers("/level1/**")
                .hasRole("学徒")
                .antMatchers("/level2/**")
                .hasAuthority("UPDATE")
                .and()
                .authorizeRequests()  // 对请求进行授权
                .anyRequest()  // 任意请求进行授权
                .authenticated() // 需要登录之后访问
                .and()
                .formLogin()   // 请求拒绝之后跳转到首页，如果没有指定则默认跳转到SpringSecurity自带的登录页面(特别丑)
                .loginPage("/index.jsp")
                .loginProcessingUrl("/do/login.html")
                .usernameParameter("loginAcct") // 登录账号请求参数名
                .passwordParameter("userPwd")
                .defaultSuccessUrl("/main.html")
                .and()
                .csrf()
                .disable() // 禁用SCRF功能
                .logout()
                .logoutUrl("/do/logout.html")
                .logoutSuccessUrl("/index.jsp")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/to/no/auth/page.html");
    }
}
