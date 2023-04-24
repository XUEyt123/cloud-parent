package org.example.cloudgateway.predicate;


import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Component
public class MyHeaderRoutePredicateFactory extends AbstractRoutePredicateFactory<MyHeaderRoutePredicateFactory.MyConfig> {

    public MyHeaderRoutePredicateFactory() {
        super(MyConfig.class);
    }

    public List<String> shortcutFieldOrder() {
        return Arrays.asList("key", "value");
    }

    @Override
    public Predicate<ServerWebExchange> apply(MyConfig config) {
        return (GatewayPredicate) exchange -> {
            // 读取配置文件中的key,value
            // - MyHeader=token,456
            String key = config.getKey();
            String value = config.getValue();
            if (StringUtils.isEmpty(value)) {
                // 如果请求中没有给value值 值给了key ，如果请求中的key和配置中的key一致就放行
                return exchange.getRequest().getHeaders().containsKey(key);
            } else {
                // 同时给了 key 和value
                // 请求中带的value和配置中的value一致就放行
                return Objects.equals(exchange.getRequest().getHeaders().getFirst(key), value);
            }
        };
    }

    public static class MyConfig {

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}

