package com.smile;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author smi1e
 * Date 2019/10/15 16:40
 * Description
 */
@Configuration
@EnableConfigurationProperties(JdbcPropertise.class)
public class JdbcCore {

    @Bean
    public DataSource datasource(JdbcPropertise jdbcPropertise) {
        System.out.println("-----------开始连接----------------");
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(jdbcPropertise.getDriver());
        dataSource.setUrl(jdbcPropertise.getUrl());
        dataSource.setPassword(jdbcPropertise.getPassword());
        dataSource.setUsername(jdbcPropertise.getUsername());
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
