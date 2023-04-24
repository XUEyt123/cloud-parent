package org.example.cloudskill.config;


import org.example.cloudcommon.config.RabbitMQConstConfig;
import org.example.cloudcommon.config.SystemConfig;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * MQ的配置类
 *
 * @author zed
 * @date 2023/01/10
 */
@Configuration
public class RabbitMQConfig {
    // 创建队列-订单同步队列 也就是下单后直接把订单添加到数据库的
    @Bean
    public Queue createq1(){
        return new Queue(RabbitMQConstConfig.Q_SKILLORDER_SYNC);
    }

    /**
     * 死信队列 需要设置一个队列过期时间，不需要绑定消费者
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue createq2(){
        HashMap<String,Object> params=new HashMap<>();
        params.put("x-dead-letter-exchange",RabbitMQConstConfig.EX_DEAD);
        params.put("x-dead-letter-routing-key",RabbitMQConstConfig.RK_DEAD_SKILLORDERTO);
        params.put("x-message-ttl", SystemConfig.SKILL_ORDER_TIMEOUT);
        return QueueBuilder.durable(RabbitMQConstConfig.Q_SKILLORDER_DEAD).withArguments(params).build();
    }

    /**
     * 延迟队列 消息到达这个队列时说明秒杀到达已经过期-就可以做取消订单的处理
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue createq3(){
        return new Queue(RabbitMQConstConfig.Q_SKILLORDER_TIMEOUT);
    }
    //创建交换器
    @Bean
    public FanoutExchange createFe(){
        return new FanoutExchange(RabbitMQConstConfig.EX_SKILLORDERADD);
    }
    @Bean
    public DirectExchange createDe(){
        return new DirectExchange(RabbitMQConstConfig.EX_DEAD);
    }

    //创建绑定
    @Bean
    public Binding createB1(FanoutExchange fe){
        return  BindingBuilder.bind(createq1()).to(fe);
    }
    @Bean
    public Binding createB2(FanoutExchange fe){
        return  BindingBuilder.bind(createq2()).to(fe);
    }
    @Bean
    public Binding createB3(DirectExchange de){
        return  BindingBuilder.bind(createq3()).to(de).with(RabbitMQConstConfig.RK_DEAD_SKILLORDERTO);
    }

}
