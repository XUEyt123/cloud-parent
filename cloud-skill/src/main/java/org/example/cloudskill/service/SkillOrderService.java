package org.example.cloudskill.service;


import org.example.cloudentity.domain.R;
import org.example.cloudskill.dto.SkillOrderAddDto;

/**
 * 秒杀订单服务
 *
 * @author zed
 * @date 2023/01/10
 */
public interface SkillOrderService {

    /**
     * 秒杀下单-第一版本
     *
     * @param dto dto
     * @param uid uid
     * @return {@link R}
     */
    R save(SkillOrderAddDto dto, Integer uid);

    /**
     * 秒杀下单-第二版本
     *
     * @param dto dto
     * @param uid uid
     * @return {@link R}
     */
    R save2(SkillOrderAddDto dto, Integer uid, String sign);


    /**
     * 查询指定用户的秒杀订单列表
     *
     * @param uid 用户ID
     * @return
     */
    R queryMy(Integer uid);

    /**
     * 查询秒杀订单详情
     *
     * @param id 秒杀订单id
     * @return {@link R}
     */
    R queryDetail(Integer id);


    /**
     * 创建秒杀下单的url接口地址
     *
     * @param dto dto 下单对象
     * @param uid uid 用户ID
     * @return {@link R}
     */
    R createUrl(SkillOrderAddDto dto, int uid);
}
