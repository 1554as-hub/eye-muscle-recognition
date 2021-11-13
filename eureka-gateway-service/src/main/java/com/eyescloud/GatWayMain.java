package com.eyescloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GatWayMain {
    public static void main(String[] args) {

        SpringApplication.run(GatWayMain.class , args);
    }

}
