package com.whw.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-05 14:27
 */
@EnableFeignClients
@SpringBootApplication
public class CrowdMainClass13 {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMainClass13.class, args);
    }
}
