package org.example.cloudorders.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;

import java.util.HashMap;
import java.util.Map;

public class CustomerBlockHandler {
    // test10方法的备选逻辑 要求必须是静态方法
    public static Map<String, Object> test10(String name, BlockException e) {
        return new HashMap<String, Object>() {{
            put("code", -1);
            put("msg", "请求过于频繁，请稍后再试!");
        }};
    }

}
