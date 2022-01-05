package com.whw.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-05 13:15
 */
@EnableZuulProxy
@SpringBootApplication
public class CrowdMainClass16 {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMainClass16.class, args);
    }
}
