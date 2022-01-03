package com.whw.security.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author 王瀚文
 * @Description:
 * @date 2022-01-02 9:38
 */
@Configuration
public class Config {

    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("172161");
        dataSource.setDriverClassName("com.mysql.jc.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/project_crowd?useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT%2B8");

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
