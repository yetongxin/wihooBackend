package com.yetx.dao;

import com.yetx.service.RedisService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String,Long> timeRedisTemplate;
    @Autowired
    private RedisService redisService;
    @Test
    public void  testZset(){
        ZSetOperations<String, String> zsetOper = stringRedisTemplate.opsForZSet();
        zsetOper.add("popu","0000001",22);
        zsetOper.add("popu","0000002",23);
        zsetOper.add("popu","0000003",15);
        zsetOper.add("popu","0000004",1);
        zsetOper.add("popu","0000005",2);
        zsetOper.add("popu","0000006",3);
        Set<String> sets = zsetOper.rangeByScore("student",10,15);
        System.out.println(sets);
        Long a = zsetOper.reverseRank("popu", "0000001");
        System.out.println("00001的rank:"+a);
        zsetOper.remove("popu","0000005");
        Set<String> rankset = zsetOper.reverseRange("popu", 0, 3);
        System.out.println(rankset);
        zsetOper.incrementScore("popu","0000001",8);
    }
    @Test
    public void testInitWork(){
        //redisService.initQuestionPopuStart();
//        Set<String> a = timeRedisTemplate.keys("qtime:");
//        System.out.println(a.size());
//        System.out.println(timeRedisTemplate.opsForValue().get("qtime:000007"));
//        for(String str:a){
//            System.out.println(str);
//        }
        redisService.zaddQuestionId("0000000012345");
        redisService.zaddQuestionId("000000001234756");
        redisService.zaddQuestionId("00000000123459326");
        redisService.initQuestionPopuWeekJob();
    }
    @Test
    public void testIfExist(){
        boolean flag1 = redisService.queryIfExist("123456");
        Assert.assertEquals(false,flag1);
        boolean flag2 = redisService.queryIfExist("000015");
        Assert.assertEquals(true,flag2);
    }
}
