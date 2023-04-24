package org.example.cloudcommon.config;

// 系统字典
public interface SystemConfig {
    // 自定义uid的消息头
    String HEADER_UID = "cp_uid";
    // 优惠券的发送类型 81：用户优惠券 82：系统优惠券
    int COUPON_SEND_USER = 81;
    int COUPON_SEND_SYSTEM = 82;

    //优惠券作用的人群：71-全体；72-会员等级 73-新用户 74-收费会员
    int COUPON_TARGET_ALL = 71;
    int COUPON_TARGET_LEVEL = 72;
    int COUPON_TARGET_NEW = 73;
    int COUPON_TARGET_PLUS = 74;

    // 批量新增用户优惠券数据的数量  线程池的任务处理的数量
    int THREAD_COUPON_BATCH = 1000;

    // 优惠券的使用状态 91-未使用 92-已使用 93-无效
    int USER_COUPON_NO = 91;
    int USER_COUPON_USED = 92;
    int USER_COUPON_DEAD = 93;

    // 商品状态 状态 101：未审核 102：审核成功 103：已上架 104：已下架 105：审核失败
    int GOODS_STATUS_ADD = 101;
    int GOODS_STATUS_SUCCESS = 102;
    int GOODS_STATUS_UP = 103;
    int GOODS_STATUS_DOWN = 104;
    int GOODS_STATUS_FAIL = 105;

    // 订单超时的时间，时间单位是毫秒
    int ORDER_TIMEOUT = 60000;//1分钟

    // 秒杀活动新创建
    Integer ACTIVITY_ADD = 1;
    // 审核通过
    Integer ACTIVITY_SUCCESS = 2;
    // 审核失败
    Integer ACTIVITY_FAIL = 3;
    // 秒杀活动删除状态
    Integer ACTIVITY_DEL = 4;

    // 秒杀下单的锁前缀
    String SKILL_ACTIVITY_LOCK = "skill:activity:lock:";
    // 秒杀订单的状态
    int SKILL_ORDER_ADD = 221;//未付款
    int SKILL_ORDER_PAYED = 222;//已付款
    int SKILL_ORDER_CONFIRM = 223;//未确认，已发货
    int SKILL_ORDER_NOCOMMENT = 224;//未评价
    int SKILL_ORDER_COMMENTED = 225;//已评价
    int SKILL_ORDER_OUT = 226; // 已超时
    int SKILL_ORDER_CANCEL = 227;//取消

    String HEADER_SIGN = "sign";

    // 每秒钟生成几个令牌
    Integer SKILL_TOKENS = 100;

    String GOODS_DETAIL_PRE = "skill-detail-pre-";

    int SKILL_ORDER_TIMEOUT = 60000;
}
