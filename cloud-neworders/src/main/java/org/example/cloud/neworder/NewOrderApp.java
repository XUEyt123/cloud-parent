package org.example.cloud.neworder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "org.example.cloudapi")
public class NewOrderApp {

    public static void main(String[] args) {
        SpringApplication.run(NewOrderApp.class, args);
    }
}
