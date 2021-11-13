package com.eyescloud.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.eyescloud.service.*")
public class DuridConfig {


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druid(){
        return  new DruidDataSource();
    }


}
