package org.example.cloudskill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SkillApp {

    public static void main(String[] args) {
        SpringApplication.run(SkillApp.class, args);
    }
}

