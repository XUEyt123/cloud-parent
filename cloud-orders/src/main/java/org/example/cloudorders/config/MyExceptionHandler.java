package org.example.cloudorders.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(1)
@Slf4j
public class MyExceptionHandler {
    // 兜底方案 优先级低
    @ExceptionHandler(Exception.class)
    public Map<String, Object> exceptionHandler(Exception e) {
        log.info("出现异常了，异常信息是:{}",e.getMessage());
        return new HashMap<String, Object>() {{
            put("code", -1);
            put("msg", "其他异常!");
        }};
    }

}
