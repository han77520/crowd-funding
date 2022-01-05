package com.whw.crowd;

import com.whw.crowd.entity.po.MemberPO;
import com.whw.crowd.mapper.MemberPOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-04 17:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyBatisTest {

    Logger logger = LoggerFactory.getLogger(MyBatisTest.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MemberPOMapper memberPOMapper;

    @Test
    public void test() throws SQLException {

        Connection connection = dataSource.getConnection();

        logger.info(connection.toString());
    }
    @Test
    public void testMapper(){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePwd = passwordEncoder.encode("172161");

        MemberPO memberPO = new MemberPO(null,"jack",encodePwd,"han77","172@qq.com",1,1,"瀚文","11",1);

        memberPOMapper.insert(memberPO);

    }

}
