package com.techie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ValidationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ValidationServiceApplication.class);
    }
}