package com.eyescloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ServiceConfigMain {

    public static void main(String[] args) {
        SpringApplication.run(ServiceConfigMain.class , args);
    }

}
