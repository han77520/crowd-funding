package com.whw.crowd.test;

import com.whw.crowd.entity.Admin;
import com.whw.crowd.mapper.AdminMapper;
import com.whw.crowd.service.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 王瀚文
 * @Description:
 * @date 2021-12-18 13:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class CrowdTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Test
    public void testLog() {
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        logger.debug("Hello, I am debug level!!!");
        logger.debug("Hello, I am debug level!!!");
        logger.debug("Hello, I am debug level!!!");

        logger.info("Info level!!!");
    }

    @Test
    public void testConnection() {
        try {
            Connection connection = dataSource.getConnection();
            System.out.println(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMapper() {

        for (int i = 0; i < 333; i++) {
            Admin admin = new Admin(null, "loginAcct" + i, "password" + i, "userName" + i, "email" + i, "2021-12-18");
            adminMapper.insert(admin);
        }


    }

    @Test
    public void testTX() {

        Admin admin = new Admin(3, "172161", "王瀚文", "ee", "hanwen", "2021-12-18");

        adminService.saveAdmin(admin);
    }
}
