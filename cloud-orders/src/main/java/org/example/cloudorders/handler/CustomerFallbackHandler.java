package org.example.cloudorders.handler;

import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import org.springframework.stereotype.Component;

@Component
public class CustomerFallbackHandler {

    public static String test11Fallback(String flag, Throwable throwable) {
        if (throwable instanceof DegradeException) {
            return "系统开小差，请稍后再试!";
        }
        return "其他异常";
    }

}
