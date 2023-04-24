package org.example.cloudjifen.controller;



import org.example.cloudentity.domain.Jifen;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("jifen")
@RefreshScope
public class JifenController {

    @Value("${pic.url}")
    String picUrl;


    /**
     * 保存积分
     */
    @PostMapping("save")
    public Map<String, Object> save(@RequestBody Jifen jifen) {
        System.out.println("调用保存积分的接口");
        System.out.println(jifen);
        System.out.println("============读取配置中心的配置项========");
        System.out.println(picUrl);
        System.out.println("============公共配置项========");

        return new HashMap<String, Object>() {{
            put("code", 200);
            put("msg", "积分保存成功");
        }};
    }

}
