package org.example.cloudskill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cloudcommon.config.RedisKeyConfig;
import org.example.cloudcommon.config.SystemConfig;
import org.example.cloudcommon.utils.RedissonUtils;
import org.example.cloudentity.domain.R;
import org.example.cloudskill.dao.TSkillactivityDao;
import org.example.cloudskill.dto.SkillActivityAddDto;
import org.example.cloudskill.dto.SkillActivityAuditDto;
import org.example.cloudskill.entity.TSkillactivity;
import org.example.cloudskill.service.SkillActivityService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SkillActivityServiceImpl implements SkillActivityService {

    private final TSkillactivityDao dao;

    /**
     * 新增秒杀活动
     *
     * @param dto dto
     * @return {@link R}
     */
    @Override
    public R save(SkillActivityAddDto dto) {
        TSkillactivity activity = new TSkillactivity();
        BeanUtils.copyProperties(dto, activity);
        activity.setFlag(SystemConfig.ACTIVITY_ADD);
        activity.setCtime(new Date());
        if (dao.insert(activity) > 0) {
            return R.ok("秒杀活动创建成功");
        }
        return R.fail("秒杀活动创建失败");
    }

    /**
     * 审核秒杀活动
     *
     * @param dto dto
     * @return {@link R}
     */
    @Override
    public R change(SkillActivityAuditDto dto) {
        // 更新秒杀活动状态
        Integer id = dto.getId();
        TSkillactivity activity = new TSkillactivity();
        activity.setId(id);
        activity.setFlag(dto.getFlag());
        if (dao.updateById(activity) > 0) {
            // 状态更新成功
            // 判断活动是审核通过 还是删除
            TSkillactivity acti = dao.selectById(id);
            if (Objects.equals(dto.getFlag(), SystemConfig.ACTIVITY_SUCCESS)) {
                // 如果活动审核通过 就把秒杀活动放入redis中
                Date date = new Date();
                long s = (acti.getStime().getTime() - date.getTime()) / 1000;
                long e = (acti.getEtime().getTime() - date.getTime()) / 1000;
                // 设置redis
                RedissonUtils.setStr(RedisKeyConfig.SKILL_ACTIVITY_NOSTART + id, 1, s);
                RedissonUtils.setStr(RedisKeyConfig.SKILL_ACTIVITY + id, acti, e);
                return R.ok("活动审核通过");
            } else if (Objects.equals(dto.getFlag(), SystemConfig.ACTIVITY_DEL)) {
                // 删除了秒杀活动 我们需要在redis中删除缓存key
                RedissonUtils.delKey(RedisKeyConfig.SKILL_ACTIVITY_NOSTART + id, RedisKeyConfig.SKILL_ACTIVITY + id);
                return R.ok("活动删除成功");
            }
        }
        return R.ok("活动审核失败");
    }

    /**
     * 用户-只能看审核通过活动
     *
     * @param flag 0.查询全部秒杀活动 1.未开始活动 2.进行中活动 3.结束的活动
     * @return {@link R}
     */
    @Override
    public R queryList(int flag) {
        // 查询审核通过的活动
        QueryWrapper<TSkillactivity> wrapper = new QueryWrapper<>();
        // where flag=2
        wrapper.eq("flag", SystemConfig.ACTIVITY_SUCCESS);
        if (flag > 0) {
            switch (flag) {
                case 1:
                    // 未开始活动 活动开始时间大于当前时间 greater than
                    // where stime > new Date()
                    wrapper.gt("stime", new Date());
                    break;
                case 2:
                    // 进行中的活动 活动开始时间大于当前时间 活动结束时间小于当前时间
                    // where stime < new Date() and etime > new Date()
                    wrapper.lt("stime", new Date()).gt("etime", new Date());
                    break;
                case 3:
                    // 结束活动 活动结束时间小于当前时间 less than
                    // where etime < new Date()
                    wrapper.lt("etime", new Date());
                    break;
            }
        }
        wrapper.orderByAsc("id");
        List<TSkillactivity> list = dao.selectList(wrapper);
        return R.ok(list);
    }

    /**
     * 查询秒杀活动倒计时
     *
     * @param id id
     * @return {@link R}
     */
    @Override
    public R queryTime(int id) {
        String key = RedisKeyConfig.SKILL_ACTIVITY_NOSTART + id;
        if (RedissonUtils.checkKey(key)) {
            long ttl = RedissonUtils.ttl(key);
            return R.ok(ttl);
        } else if (RedissonUtils.checkKey(RedisKeyConfig.SKILL_ACTIVITY + id)) {
            return R.ok("秒杀活动进行中");
        }
        return R.ok("秒杀活动不存在");
    }
}
