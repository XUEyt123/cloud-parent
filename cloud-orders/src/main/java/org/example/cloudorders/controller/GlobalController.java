package org.example.cloudorders.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("global")
@Api(tags = "全局控制器")
public class GlobalController {
    // 演示QPS限流
    @GetMapping("test1")
    @SentinelResource("test1")
    public String test1(String name) {
        return "Order Test1! " + name;
    }

    // 演示异常熔断
    @GetMapping("test2")
    @SentinelResource("test2")
    public String test2(String flag) {
        if (Objects.isNull(flag)) {
            throw new IllegalArgumentException("非法参数异常！");
        }
        return "Order Test2! " + flag;
    }

}
