package org.example.cloudorders.interceptor;


import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomerRequestInterceptor implements RequestInterceptor {

    @Value("${spring.application.name}")
    String serviceName;

    @Override
    public void apply(feign.RequestTemplate requestTemplate) {
        // 订单服务发出去的请求 统一加一个请求头
        requestTemplate.header("source",serviceName);
    }
}

