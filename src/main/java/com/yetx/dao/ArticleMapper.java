package com.yetx.dao;

import com.yetx.pojo.Article;
import com.yetx.vo.ArticleVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper {
    int deleteByPrimaryKey(String id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(String id);

    List<Article> selectByOpenid(String openid);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);


    List<ArticleVO> selectByPopularity();


}