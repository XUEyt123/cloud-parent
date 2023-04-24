package org.example.cloudcoupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CouponApp {

    public static void main(String[] args) {
        SpringApplication.run(CouponApp.class,args);
    }

}

