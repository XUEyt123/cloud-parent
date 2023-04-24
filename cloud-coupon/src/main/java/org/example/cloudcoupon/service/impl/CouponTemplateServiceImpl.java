package org.example.cloudcoupon.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cloudcommon.config.MqMsgBo;
import org.example.cloudcommon.config.RabbitMQConstConfig;
import org.example.cloudcommon.config.SystemConfig;
import org.example.cloudcommon.utils.SnowFlowUtil;
import org.example.cloudcommon.utils.ThreadPoolSingle;
import org.example.cloudcoupon.config.CouponAudit;
import org.example.cloudcoupon.dao.TCouponTemplateDao;
import org.example.cloudcoupon.dao.TUsercouponDao;
import org.example.cloudcoupon.entity.TCouponTemplate;
import org.example.cloudcoupon.entity.TUsercoupon;
import org.example.cloudcoupon.service.CouponTemplateService;
import org.example.cloudentity.domain.R;
import org.example.cloudentity.dto.CouponAddDto;
import org.example.cloudentity.dto.CouponAuditDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponTemplateServiceImpl implements CouponTemplateService {

    private final TCouponTemplateDao templateDao;
    private final TUsercouponDao couponDao;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public R<PageInfo<TCouponTemplate>> queryAll(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<TCouponTemplate> list = templateDao.queryAll();
        PageInfo<TCouponTemplate> info = new PageInfo<>(list);
        return R.ok(info);
    }

    @Override
    public R save(CouponAddDto dto) {
        // dto => entity
        TCouponTemplate template = new TCouponTemplate();
        BeanUtils.copyProperties(dto, template);
        // 补全数据
        // 因为还没有用户模块 也没有设置ThreadLocal 所以先模拟用户ID
        template.setUserId(1);
        template.setCreateTime(new Date());
        // 优惠券状态
        template.setFlag(CouponAudit.未审核.getCode());
        // 优惠券的唯一标识可以用雪花算法生成
        long templateKey = SnowFlowUtil.getInstance().nextId();
        template.setTemplateKey(templateKey + "");
        if (templateDao.insert(template) > 0) {
            return R.ok("优惠券创建成功");
        }
        return R.fail("优惠券创建失败");
    }

    /**
     * 优惠券审核接口
     *
     * @param dto dto
     * @return {@link R}
     */
    @Override
    public R audit(CouponAuditDto dto) {
        // 根据模板ID查询优惠券
        TCouponTemplate template = templateDao.queryById(dto.getId());
        // 校验优惠券模板的状态
        if (Objects.nonNull(template) && Objects.equals(template.getFlag(), CouponAudit.未审核.getCode())) {
            // 开始审核优惠券 更新优惠券模板
            template.setFlag(dto.getFlag()); // 状态
            template.setUserId(dto.getAid()); // 审核人
            template.setUserAudit(dto.getInfo()); // 审核意见
            if (templateDao.update(template) > 0) {
                // 判断审核状态是【通过】 【拒绝】
                // 如果状态是通过了 那么就需要开始发送优惠券
                if (Objects.equals(dto.getFlag(), CouponAudit.审核通过.getCode())) {
                    // 发放优惠券，先写第一版本直接操作数据库的
                    // 本质就是到用户优惠券表中新增数据
                    // 获取给哪些用户发放优惠券

                    // 把优惠券模板发送到MQ消息队列中
                    // 构建一个MQ消息对象
                    MqMsgBo bo = new MqMsgBo();
                    bo.setId(SnowFlowUtil.getInstance().nextId());
                    bo.setData(template);
                    // 设置消息类型
                    Integer sendType = template.getSendType();
                    // 路由key
                    String rk = "";
                    if (Objects.equals(sendType, SystemConfig.COUPON_SEND_SYSTEM)) {
                        // 系统优惠券
                        bo.setType(RabbitMQConstConfig.MQTYPE_COUPONSYS);
                        rk = RabbitMQConstConfig.RK_COUPONSYS;
                    } else if (Objects.equals(sendType, SystemConfig.COUPON_SEND_USER)) {
                        // 用户优惠券
                        bo.setType(RabbitMQConstConfig.MQTYPE_COUPONUSE);
                        rk = RabbitMQConstConfig.RK_COUPONUSE;
                    }
                    // 发布消息到MQ
                    rabbitTemplate.convertAndSend(RabbitMQConstConfig.EX_COUPONTEM, rk, bo);
                    return R.ok("优惠券审核通过");
                }
            } else {
                return R.ok("优惠券审核拒绝");
            }
        }
        return R.fail("亲，非法操作");
    }


}


