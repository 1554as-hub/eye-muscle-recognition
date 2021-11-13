package com.eyescloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableEurekaClient
@EnableOAuth2Client
@EnableResourceServer
@MapperScan("com.eyescloud.mapper")
public class FileService {

    public static void main(String[] args) {
        SpringApplication.run(FileService.class , args);
    }

}
