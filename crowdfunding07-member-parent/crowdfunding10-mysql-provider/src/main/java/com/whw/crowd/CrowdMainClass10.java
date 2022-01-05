package com.whw.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-04 17:44
 */
@MapperScan("com.whw.crowd.mapper")
@SpringBootApplication
public class CrowdMainClass10 {

    public static void main(String[] args) {
        SpringApplication.run(CrowdMainClass10.class, args);
    }

}
