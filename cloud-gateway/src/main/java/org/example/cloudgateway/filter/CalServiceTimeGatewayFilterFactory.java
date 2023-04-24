package org.example.cloudgateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class CalServiceTimeGatewayFilterFactory extends AbstractGatewayFilterFactory<CalServiceTimeGatewayFilterFactory.MyConfig> {

    public CalServiceTimeGatewayFilterFactory() {
        super(MyConfig.class);
    }

    @Override
    public ShortcutType shortcutType() {
        return ShortcutType.DEFAULT;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("key", "value");
    }

    @Override
    public GatewayFilter apply(MyConfig config) {
        return (exchange, chain) -> {
            // 获取请求到达过滤器的时间
            long startTime = System.currentTimeMillis();
            // 放行 让过滤器链继续执行
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                // 请求结束 要离开过滤器时执行
                long endTime = System.currentTimeMillis();
                System.out.println("服务调用耗时:" + (endTime - startTime) + "毫秒");
            }));
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

