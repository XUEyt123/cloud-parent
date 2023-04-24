package org.example.cloudorders;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
// basePackages 指定feign接口所在的包 程序启动时会扫描组件
@EnableFeignClients(basePackages = "org.example.cloudapi") // 这个注解开启OpenFeign
public class OrdersApp {

    public static void main(String[] args) {
        SpringApplication.run(OrdersApp.class,args);
    }

    // RestTemplate 作用是帮助我们发送REST请求的
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

