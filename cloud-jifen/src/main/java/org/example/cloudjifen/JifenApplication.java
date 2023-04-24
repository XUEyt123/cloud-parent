package org.example.cloudjifen;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class JifenApplication {

    public static void main(String[] args) {
        SpringApplication.run(JifenApplication.class,args);
    }
}
