package com.whw.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-04 16:29
 */
@EnableEurekaServer
@SpringBootApplication
public class CrowdMainClass08 {

    public static void main(String[] args) {
        SpringApplication.run(CrowdMainClass08.class, args);
    }
}
