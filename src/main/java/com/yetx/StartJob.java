package com.yetx;

import com.yetx.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartJob implements ApplicationRunner {

    @Autowired
    private RedisService redisService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("启动任务开始");
        redisService.initQuestionPopuStart();
    }
}
