package org.example.cloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApp {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
    }
    // 验证全局过滤器【GlobalFilter】的执行顺序
    // Order注解中的值越小越优先执行

    @Bean
    @Order(-1)
    public GlobalFilter aFilter() {
        return (exchange, chain) -> {
            System.out.println("A过滤器执行了.>>>>>>");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                System.out.println("A过滤器执行了.<<<<<");
            }));
        };
    }

    @Bean
    @Order(0)
    public GlobalFilter bFilter() {
        return (exchange, chain) -> {
            System.out.println("B过滤器执行了.>>>>>>");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                System.out.println("B过滤器执行了.<<<<<");
            }));
        };
    }

    @Bean
    @Order(1)
    public GlobalFilter cFilter() {
        return (exchange, chain) -> {
            System.out.println("C过滤器执行了.>>>>>>");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                System.out.println("C过滤器执行了.<<<<<");
            }));
        };
    }

}
