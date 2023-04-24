package org.example.cloudcoupon.config;


import org.example.cloudcommon.config.RabbitMQConstConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    // 系统优惠券的队列
    @Bean
    public Queue createSys(){
        return new Queue(RabbitMQConstConfig.Q_COUPONSYS);
    }

    // 用户优惠券的队列
    @Bean
    public Queue createUser(){
        return new Queue(RabbitMQConstConfig.Q_COUPONUSE);
    }

    // 优惠券模板专用的交换机
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(RabbitMQConstConfig.EX_COUPONTEM);
    }

    // 系统优惠券队列的绑定
    @Bean
        public Binding bindSys(DirectExchange e){
        return BindingBuilder.bind(createSys()).to(e).with(RabbitMQConstConfig.RK_COUPONSYS);
    }

    // 用户优惠券队列的绑定
    @Bean
    public Binding bindUser(DirectExchange e){
        return BindingBuilder.bind(createUser()).to(e).with(RabbitMQConstConfig.RK_COUPONUSE);
    }
}
