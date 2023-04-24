package org.example.cloudorders.controller;


import org.example.cloudapi.JifenApi;
import org.example.cloudentity.domain.*;
import org.example.cloudorders.service.OrdersService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("orders")
public class OrdersController {
    @Resource
    private RestTemplate restTemplate;

    @Resource
    JifenApi jifenApi;
    @Resource
    private OrdersService ordersService;


    /**
     * 下单 保存订单
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping("save")
    public Map<String, Object> save() {
        // 调用商品服务 查询商品 => 需要远程调用商品服务
        Goods goods = restTemplate.getForObject("http://cloud-goods/goods/2", Goods.class);
        System.out.println("获取到的商品对象是:" + goods);
        return new HashMap<String, Object>() {
            {
                put("code", 200);
                put("msg", "下单成功");
            }
        };
    }


    /**
     * 下单 时保存积分
     * 调用积分服务
     */
    @GetMapping("save2")
    public Map<String, Object> save2() {
        Jifen jifen = new Jifen(1, 100, "下单宋积分");
        System.out.println("远程调用积分服务");
        System.out.println(jifenApi.save(jifen));
        return new HashMap<String, Object>() {
            {
                put("code", 200);
                put("msg", "下单成功");
            }
        };
    }
    @GetMapping("{id}")
    public R<Order> findById(@PathVariable("id") Integer id, @RequestHeader("name") String name,@RequestParam("age")Integer age) {
        System.out.println("从网关的请求头中获取的name=" + name);
        System.out.println("从网关的请求参数中获取的age=" + age);

        return R.ok(new Order(id, 200, "下单"));
    }

    @PostMapping("saveOrder")
    public R saveOrder(@RequestBody TbOrder order) {
        return ordersService.saveOrder(order);
    }

}
