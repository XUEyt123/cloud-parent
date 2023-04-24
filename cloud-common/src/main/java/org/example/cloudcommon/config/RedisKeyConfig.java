package org.example.cloudcommon.config;

// 记录所有的key
public interface RedisKeyConfig {
    //存储优惠券信息
    //追加优惠券模板id,值存储数量，有效期为优惠券模板的领取的结束时间
    //public static final String COUPON_CACHE="cp:soupon:";//追加:模板id:等级id
    String COUPON_CACHE="cp:coupon:";//追加:模板id List类型，第一个元素：数量 第二个元素：等级要求
    //存储用户领过的优惠券，用来解决 用户领取优惠券的限领的问题,有效期：优惠券模板的领取的时间
    String COUPON_USERS="cp:coupon:users:";//追加模板id,Set类型 值记录uid,有效期

    // 设置分布式锁的key,优惠券的领取 防止超领
    String COUPON_LOCK="cp:coupon:rl:";//追加模板id
    int COUPON_LOCK_TIME=10;//10秒

    // Redis 存储新订单
    String ORDER_ADD="cp:orders";//Hash类型 存储所有的新订单 没有有效期

    // 未开始的秒杀活动
    String SKILL_ACTIVITY_NOSTART = "skill:activity:nostart:";
    // 进行中的秒杀活动
    String SKILL_ACTIVITY = "skill:activity:start:";
    // 秒杀活动商品的前缀
    String SKILL_GOODS = "skill:goods:";
    // 秒杀服务的分布式锁
    String SKILL_ACTIVITY_LOCK = "skill:activity:lock:";

    String SKILL_URL = "cp:skill:url:"; //秒杀商品id+uid 值：密文 有效期 3秒

    // 生成的动态下单地址 key的过期时间
    int SKILL_URL_TIME = 300; //300秒 方便测试，实际场景下可以设置3秒

    // 记录ip的访问频率
    String IP_REQUESTS = "cp:ips:";//追加ip
    int IP_REQUESTS_TIMES = 1;//秒

    String IPS = "cp:ips";// Set类型

    String SKILL_USERS = "cp:USERS";// Set类型

}
