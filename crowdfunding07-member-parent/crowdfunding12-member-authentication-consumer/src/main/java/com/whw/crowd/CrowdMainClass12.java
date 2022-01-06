package com.whw.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-05 12:53
 */
@EnableFeignClients
@SpringBootApplication
public class CrowdMainClass12 {

    public static void main(String[] args) {
        SpringApplication.run(CrowdMainClass12.class, args);
    }
}
