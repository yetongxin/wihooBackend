package com.yetx.dao;

import com.yetx.dto.UserDTO;
import com.yetx.pojo.User;
import com.yetx.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(UserTest.class);
    @Test
    public void findAllarticle(){
//        UserDTO userDTO = userMapper.selectAllArticleByOpenid("openidtest");
//        logger.info("userDTO:{}",userDTO);
//        logger.info("articles:{}",userDTO.getArticles().toString());
//        UserDTO userDTO1 = userMapper.selectAllQuestionByOpenid("openidtest");
//        logger.info("userDTO1:{}",userDTO1);
//        logger.info("question:{}",userDTO1.getQuestions().toString());
//        UserDTO userDTO2 = userMapper.selectAllAnswerByOpenid("openidtest");
//        logger.info("userDTO2:{}",userDTO2);
//        logger.info("answer:{}",userDTO2.getAnswers().toString());
//        userService.findAllArticle("openidtest",1,2);
        UserDTO userDTO = userMapper.selectFollowsByOpenid("213321");
        logger.info(userDTO.toString());
    }

}
