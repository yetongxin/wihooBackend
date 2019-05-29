package com.yetx.dao;

import com.yetx.pojo.Question;
import com.yetx.vo.QuestionVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuestionMapperTest {
    @Autowired
    private QuestionMapper questionMapper;
    private Logger logger = LoggerFactory.getLogger(QuestionMapperTest.class);
    @Test
    public void searachTest(){
        List<String> keywords = new ArrayList<>();
        keywords.add("好吃");
        keywords.add("哪里");
        List<QuestionVO> questions = questionMapper.selectByKeyWords(keywords);
        logger.info(String.valueOf(questions));
        Assert.assertEquals(5,questions.size());
    }
}
