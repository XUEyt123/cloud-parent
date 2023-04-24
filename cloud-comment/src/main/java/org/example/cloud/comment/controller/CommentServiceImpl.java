package org.example.cloud.comment.controller;


import org.example.cloud.comment.service.CommentService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {
    @Override
    // fixedRate 固定频率执行任务 单位是毫秒
    // @Scheduled(fixedRate = 2000)
    // 每天的每小时的每分钟的0秒开始 每2秒执行一次
    @Scheduled(cron = "0/2 * * * * ?")
    public void autoComment() {
        System.out.println("定时任务执行了。。 自动评价订单" + new Date());
    }
}
