package com.whw.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-05 15:27
 */
@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/auth/member/to/registry/page").setViewName("member_reg");
        registry.addViewController("/auth/member/to/login/page").setViewName("member_login");
    }
}
