package org.example.cloudskill.service.impl;


import org.example.cloudcommon.config.MqMsgBo;
import org.example.cloudcommon.config.RabbitMQConstConfig;
import org.example.cloudcommon.config.RedisKeyConfig;
import org.example.cloudcommon.config.SystemConfig;
import org.example.cloudcommon.utils.RedissonUtils;
import org.example.cloudcommon.utils.SnowFlowUtil;
import org.example.cloudentity.domain.R;
import org.example.cloudskill.dao.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cloudskill.dto.SkillOrderAddDto;
import org.example.cloudskill.entity.*;
import org.example.cloudskill.service.SkillOrderService;
import org.redisson.api.RLock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class SkillOrderServiceImpl implements SkillOrderService {

    private final TSkillactivityDao activityDao;
    private final TSkillorderDao orderDao;
    private final TSkillgoodsDao goodsDao;
    // 秒杀订单记录
    private final TSkillorderlogDao orderLogDao;
    // 秒杀记录
    private final TSkilllogDao logDao;

    private final RabbitTemplate rabbitTemplate;


    /**
     * 秒杀下单
     *
     * @param dto dto
     * @param uid uid
     * @return {@link R}
     */
    @Override
    public R save(SkillOrderAddDto dto, Integer uid) {
        Integer sgid = dto.getSgid();
        RLock rLock = RedissonUtils.getLock(RedisKeyConfig.SKILL_ACTIVITY_LOCK + sgid);
        try {
            if (rLock.tryLock(10, TimeUnit.SECONDS)) {
                // 获取到锁了
                // 校验秒杀活动是否进行中 第一版本 先用数据库校验活动是否进行中
                Integer said = dto.getSaid();
                TSkillactivity activity = activityDao.selectById(said);
                // 判断活动是否存在
                if (Objects.nonNull(activity)) {
                    Date now = new Date();
                    if (activity.getStime().before(now) && activity.getEtime().after(now)) {
                        // 活动进行中
                        // 验证购买的数量是否超出约定的上限
                        if (activity.getMaxcount() > dto.getNum()) {
                            // 校验一下 秒杀商品购买量 不能超过最大购买量
                            List<TSkillorder> orders = orderDao.queryByUidAndSgid(uid, sgid);
                            if (CollectionUtils.isEmpty(orders)) {
                                // 校验库存
                                TSkillgoods goods = goodsDao.queryById(sgid);
                                if (goods.getStock() >= dto.getNum()) {
                                    // 开始下单
                                    // 库存够，可以下单
                                    // 生成订单信息
                                    TSkillorder order = new TSkillorder(SnowFlowUtil.getInstance().nextId() + "",
                                            sgid, goods.getCurrprice(), dto.getNum(), dto.getUaid(), uid);
                                    if (orderDao.insert(order) > 0) {

                                        //7.扣减库存
                                        TSkillgoods sg = new TSkillgoods();
                                        sg.setId(sgid);
                                        sg.setStock(goods.getStock() - dto.getNum());
                                        goodsDao.update(sg);

                                        //8.秒杀成功-记录
                                        orderLogDao.insert(new TSkillorderlog(order.getId(), SystemConfig.SKILL_ORDER_ADD, "秒杀订单生成！"));
                                        logDao.insert(new TSkilllog(uid, sgid, "1"));//记录秒杀结果

                                        //9.返回
                                        return R.ok();
                                    } else {
                                        return R.fail("亲，库存不够了");
                                    }
                                } else {
                                    return R.fail("亲，没人限购一件呀");
                                }
                            } else {
                                return R.fail("下单超过最大购买量了");
                            }
                        }
                    } else {
                        return R.fail("活动不存在");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }
        return R.fail("秒杀下单失败");
    }

    @Override
    public R save2(SkillOrderAddDto dto, Integer uid, String sign) {
        // 添加分布式锁
        Integer sgid = dto.getSgid();
        RLock lock = RedissonUtils.getLock(RedisKeyConfig.SKILL_ACTIVITY_LOCK + sgid);
        try {
            if (lock.tryLock(10, TimeUnit.SECONDS)) {
                // 校验商品库存是否充足
                String key = RedisKeyConfig.SKILL_GOODS + dto.getSaid();
                if (RedissonUtils.checkKey(key)) {
                    if (RedissonUtils.existsField(key, sgid + "")) {
                        int stock = (int) RedissonUtils.getHash(key, sgid + "");
                        if (stock >= dto.getNum()) {
                            // 开始下单
                            // 更新库存 更新redis 生成订单对象 通过MQ发布消息到交换机
                            TSkillgoods goods = new TSkillgoods();
                            goods.setId(sgid);
                            int newStock = stock - dto.getNum();
                            goods.setStock(newStock);
                            if (goodsDao.update(goods) > 0) {
                                RedissonUtils.setHash(key, sgid + "", newStock);
                                //生成订单信息
                                TSkillorder order = new TSkillorder(SnowFlowUtil.getInstance().nextId() + "",
                                        dto.getSgid(), dto.getPrice(), dto.getNum(), dto.getUaid(), uid);
                                // 发送订单对象到MQ中
                                MqMsgBo bo = new MqMsgBo();
                                bo.setId(SnowFlowUtil.getInstance().nextId());
                                bo.setType(RabbitMQConstConfig.MQTYPE_SKILLORDERADD);
                                bo.setData(order);
                                rabbitTemplate.convertAndSend(RabbitMQConstConfig.EX_SKILLORDERADD, "", bo);
                                return R.ok("秒杀下单成功");
                            }
                        }
                    }
                } else {
                    return R.fail("亲，您来晚了活动已结束");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return R.fail("秒杀下单失败");
    }

    @Override
    public R queryMy(Integer uid) {
        return null;
    }

    @Override
    public R queryDetail(Integer id) {
        return null;
    }

    @Override
    public R createUrl(SkillOrderAddDto dto, int uid) {
        return null;
    }
}
