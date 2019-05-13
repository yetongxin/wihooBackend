package com.yetx.service.impl;

import com.github.pagehelper.PageInfo;
import com.yetx.dao.UserTest;
import com.yetx.pojo.Article;
import com.yetx.pojo.User;
import com.yetx.service.UserService;
import com.yetx.vo.PageVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);


    @Autowired
    UserService userService;
    @Test
    public void getUserById() {
        User user= userService.findByOpenid("1a2b3c4d5e");
        Assert.assertEquals(java.util.Optional.of(12).get(),user.getFollowCounts());
    }
    @Test
    public void getAllArticle(){
        PageVO res = userService.findAllArticle("TOKENTEST", 1, 10);
        logger.info(String.valueOf(res.getCurData()));
        logger.info(String.valueOf(res.getPageNum()));
    }
}