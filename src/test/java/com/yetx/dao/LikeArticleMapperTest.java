package com.yetx.dao;

import com.yetx.pojo.LikeArticle;
import com.yetx.service.ArticleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LikeArticleMapperTest {

    @Autowired
    private LikeArticleMapper likeArticleMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleService articleService;
    @Test
    public void selectByUserIdAndArticleId() {
        articleMapper.selectByPrimaryKey("000001");
        LikeArticle likeArticle = likeArticleMapper.selectByUserIdAndArticleId("testId","00001");
        Assert.assertEquals("000001",likeArticle.getId());

    }
}