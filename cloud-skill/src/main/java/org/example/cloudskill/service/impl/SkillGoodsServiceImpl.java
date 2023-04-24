package org.example.cloudskill.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cloudcommon.config.RedisKeyConfig;
import org.example.cloudcommon.config.SystemConfig;
import org.example.cloudcommon.utils.RedissonUtils;
import org.example.cloudentity.domain.R;
import org.example.cloudskill.dao.TSkillgoodsDao;
import org.example.cloudskill.dto.SkillGoodsAddDto;
import org.example.cloudskill.dto.SkillGoodsDetailDto;
import org.example.cloudskill.dto.SkillGoodsDto;
import org.example.cloudskill.entity.TSkillgoods;
import org.example.cloudskill.service.SkillGoodsService;
import org.example.cloudskill.utils.FreeMarkerUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SkillGoodsServiceImpl implements SkillGoodsService {

    private final TSkillgoodsDao goodsDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R save(SkillGoodsAddDto dto) {
        TSkillgoods goods = new TSkillgoods();
        BeanUtils.copyProperties(dto, goods);
        if (goodsDao.insert(goods) > 0) {
            return R.ok("秒杀商品新增成功");
        }
        return R.ok("秒杀商品新增失败");
    }

    // 秒杀商品的上架
    @Override
    public R up(SkillGoodsDto dto) {
        TSkillgoods goods = new TSkillgoods();
        goods.setId(dto.getId());
        goods.setFlag(dto.getFlag());
        // 1、判断商品的状态 是否是上架
        if (Objects.equals(dto.getFlag(), SystemConfig.GOODS_STATUS_UP)) {
            // 要进行商品上架： 更新表 生成静态页面放到服务器上/MinIO/COS/OSS
            // skill-detail-pre-1.html
            goods.setHtmlurl(SystemConfig.GOODS_DETAIL_PRE + dto.getId() + ".html");
            if (goodsDao.update(goods) > 0) {
                // 更新成功后 需要在指定的位置生成静态页面
                // 获取秒杀商品详情的DTO对象
                SkillGoodsDetailDto goodsDetailDto = goodsDao.findGoodsDetailDTOById(dto.getId());
                // 生成静态页面
                FreeMarkerUtil.createHtml(goodsDetailDto);
                // 把秒杀商品的库存放入redis中
                // 把这个秒杀活动中的所有商品库存信息放入一个hash结构中
                String key = RedisKeyConfig.SKILL_GOODS + goodsDetailDto.getSaid();
                if (RedissonUtils.checkKey(key)) {
                    // key 已经存在
                    RedissonUtils.setHash(key, goodsDetailDto.getSgid() + "", goodsDetailDto.getStock());
                } else {
                    // key 之前不存在
                    RedissonUtils.setHash(key, goodsDetailDto.getSgid() + "", goodsDetailDto.getStock());
                    // 设置key的过期时间 过期时间应该跟秒杀活动的过期时间一致
                    RedissonUtils.expire(key, RedissonUtils.ttl(RedisKeyConfig.SKILL_ACTIVITY + goodsDetailDto.getSaid()));
                }
                return R.ok("商品上架成功");
            }
        } else {
            // 如果是其他状态 直接更新商品表
            if (goodsDao.update(goods) > 0) {
                return R.ok("商品状态更新成功");
            }
        }
        return R.fail("商品状态更新失败");
    }

    @Override
    public R queryList(int said) {
        List<TSkillgoods> list = goodsDao.findBySaid(said);
        return R.ok(list);
    }

}
