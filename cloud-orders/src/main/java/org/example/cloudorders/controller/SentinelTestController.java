package org.example.cloudorders.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.example.cloudapi.JifenApi;
import org.example.cloudentity.domain.Jifen;
import org.example.cloudorders.handler.CustomerBlockHandler;
import org.example.cloudorders.handler.CustomerFallbackHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("sentinel")
public class SentinelTestController {

    @Resource
    JifenApi jifenApi;

    /**
     * 测试限流
     */
    @GetMapping("test1")
    public String test1() {
        return "Test1";
    }

    /**
     * 关联资源
     */
    @GetMapping("test2")
    public String test2() {
        return "Order Test2!";
    }

    /**
     * 测试链路限流
     */
    @GetMapping("test3")
    public String test3() {
        System.out.println("Test3接口调用积分服务");
        jifenApi.save(new Jifen(1, 100, "注册送积分"));
        return "Order Test3";
    }

    /**
     * 测试链路限流
     */
    @GetMapping("test4")
    public String test4() {
        System.out.println("Test4接口调用积分服务");
        jifenApi.save(new Jifen(1, 100, "登录送积分"));
        return "Order Test4";
    }

    /**
     * 测试熔断降级-满调用比例
     */
    @GetMapping("test5")
    public String test5(String flag) throws InterruptedException {
        if (StringUtils.isEmpty(flag)) {
            // 如果没有给flag参数 就休眠2秒
            TimeUnit.SECONDS.sleep(2);
        }
        return "Order Test5!";
    }

    /**
     * 测试熔断降级-异常比例和异常数
     */
    @GetMapping("test6")
    public String test6(String flag) {
        if (StringUtils.isEmpty(flag)) {
            // 如果没有给flag参数 就抛异常
            throw new IllegalArgumentException("flag不能为空");
        }
        return "Order Test6!";
    }

    /**
     * test7
     * 热点参数限流
     * 1：必须有一个参数，参数的值可以任意
     * 2：接口访问qps>1
     * SentinelResource 手动指定资源名称的
     * 如果不指定资源名称  默认的资源名称就是接口的访问路径
     */
    @GetMapping("test7")
    @SentinelResource("test7-hotkey")
    public String test7(String name) {
        System.out.println("参数名字是:" + name);
        return name;
    }

    /**
     * 自定义限流异常
     * <p>
     * 主逻辑 ： 如果主逻辑背被限流了 流程会转到备选逻辑执行
     */
    @GetMapping("test9")
    @SentinelResource(value = "test9", blockHandler = "test9Handler")
    public String test9(String name) {
        System.out.println(name);
        return "Order test9!";
    }


    public String test9Handler(String name, BlockException e) {
        System.out.println(name);
        return "请求过于频繁，请稍后再试!";
    }


    /**
     * 优化自定义限流异常
     * 备选逻辑放到单独的处理类中
     */
    @GetMapping("test10")
    @SentinelResource(value = "test10", blockHandlerClass = CustomerBlockHandler.class, blockHandler = "test10")
    public Map<String, Object> test10(String name) {
        System.out.println(name);
        return new HashMap<String, Object>() {{
            put("code", 200);
            put("msg", "请求成功");
        }};
    }

    // 主逻辑 - 熔断降级的自定义异常
    @GetMapping("test11")
    @SentinelResource(value = "test11", fallback = "test11Fallback",fallbackClass = CustomerFallbackHandler.class)
    public String test11(String flag) {
        if (Objects.isNull(flag)) {
            throw new IllegalArgumentException("非法参数异常！");
        }
        return "Order Test11! " + flag;
    }

}
