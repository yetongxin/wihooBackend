package com.yetx;

import com.yetx.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//TODO:start work;
@Component
public class ScheduledPopuWork {

    @Autowired
    private RedisService redisService;

    @Scheduled(cron = "0 0 3 ? * 1")
    public void catalogContScheduled(){
        redisService.initQuestionPopuWeekJob();
    }
}
