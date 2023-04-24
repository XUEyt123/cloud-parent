package org.example.cloudentity.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Jifen {
    // ID
    private Integer jifenId;

    // 积分数
    private Integer count;

    // 积分类型
    private String type;

}
