package org.example.cloudgateway.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 身份验证全局过滤器
 *
 * @author zed
 * @date 2023/03/11
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 从请求头中获取token 校验token的值是123 校验通过放行 如果不是都拦截
        String token = request.getHeaders().getFirst("token");
        if (StringUtils.isEmpty(token)) {
            // 没有token 说明没有登录过 拦截
            Map<String, Object> map = new HashMap<>();
            map.put("code", 403);
            map.put("msg", "您还没有登录");
            return response(response, map);
        } else {
            if (Objects.equals(token, "123")) {
                // 认为token有效
                return chain.filter(exchange);
            } else {
                Map<String, Object> map = new HashMap<>();
                map.put("code", 403);
                map.put("msg", "token无效");
                return response(response, map);
            }
        }
    }


    private Mono<Void> response(ServerHttpResponse response, Object msg) {
        String resJson = "";
        try {
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            resJson = new ObjectMapper().writeValueAsString(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataBuffer dataBuffer = response.bufferFactory().wrap(resJson.getBytes());
        return response.writeWith(Flux.just(dataBuffer));//响应json数据
    }

    // 数值越小 过滤器越优先执行
    @Override
    public int getOrder() {
        return 0;
    }
}

