package com.eyescloud;


import com.eyescloud.entity.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@EnableEurekaClient
@EnableResourceServer
@MapperScan("com.eyescloud.mapper")
public class SecurityMain {


    public static void main(String[] args) {
        SpringApplication.run(SecurityMain.class , args);
    }


    @RequestMapping(value = "/user" , produces = "application/json")
    public Map<String , Object> user (@AuthenticationPrincipal User user){
        Map<String , Object> userInfo = new HashMap<>();
        user.setPassword("");
        userInfo.put( "user" , user);

        return  userInfo;

    }



}
