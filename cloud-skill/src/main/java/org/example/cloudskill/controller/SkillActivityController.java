package org.example.cloudskill.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cloudentity.domain.R;
import org.example.cloudskill.dto.SkillActivityAddDto;
import org.example.cloudskill.dto.SkillActivityAuditDto;
import org.example.cloudskill.service.SkillActivityService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 秒杀活动控制器
 *
 * @author zed
 * @date 2022/11/14
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("skill/activity")
public class SkillActivityController {

    @Resource
    private SkillActivityService activityService;

    /**
     * 新增秒杀活动
     *
     * @param dto dto
     * @return {@link R}
     */
    @PostMapping("save")
    public R save(@RequestBody SkillActivityAddDto dto) {
        // 参数校验 Assert
        return activityService.save(dto);
    }

    /**
     * 查询列表
     *
     * @param flag 状态
     * @return {@link R}
     */
    @GetMapping("query/{flag}")
    public R queryList(@PathVariable("flag") Integer flag) {
        Assert.isTrue(flag >= 0, "活动状态必须是大于等于0的整数");
        return activityService.queryList(flag);
    }

    /**
     * 修改活动状态
     *
     * @param dto dto
     * @return {@link R}
     */
    @PostMapping("change")
    public R change(@RequestBody SkillActivityAuditDto dto) {
        Assert.notNull(dto.getId(), "ID不能为空");
        Assert.notNull(dto.getFlag(), "状态不能为空");
        return activityService.change(dto);
    }

    /**
     * 查询秒杀活动倒计时
     *
     * @param id id
     * @return {@link R}
     */
    @PostMapping("time/{id}")
    public R queryTime(@PathVariable("id") Integer id) {
        Assert.notNull(id, "ID不能为空");
        return activityService.queryTime(id);
    }

}
