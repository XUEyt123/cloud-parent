package org.example.cloudorders.config;

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(0)

public class GlobalExceptionHandler {
    // 限流异常的处理方法
    @ExceptionHandler(FlowException.class)
    public Map<String, Object> flowExceptionHandler() {
        return new HashMap<String, Object>() {{
            put("code", -1);
            put("msg", "请求过于频繁，稍后再试!");
        }};
    }

    // 熔断降级异常的处理方法
    @ExceptionHandler(DegradeException.class)
    public Map<String, Object> degradeExceptionHandler() {
        return new HashMap<String, Object>() {{
            put("code", -1);
            put("msg", "系统开小差，稍后再试!");
        }};
    }

    // 权限异常的处理方法
    @ExceptionHandler(AuthorityException.class)
    public Map<String, Object> authorityExceptionHandler() {
        return new HashMap<String, Object>() {{
            put("code", -1);
            put("msg", "没有权限访问!");
        }};
    }

}
