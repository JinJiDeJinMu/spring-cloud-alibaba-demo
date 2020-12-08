package com.hjb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.hjb.mapper")
public class CloudUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudUserApplication.class, args);
    }

}
