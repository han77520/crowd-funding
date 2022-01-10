package com.whw.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-09 22:47
 */
@EnableFeignClients
@SpringBootApplication
public class CrowdMainClass15 {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMainClass15.class, args);
    }
}
