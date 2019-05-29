package com.yetx.dao;

import com.yetx.constant.ZanType;
import com.yetx.pojo.LikeArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Repository
public interface LikeArticleMapper {
    int deleteByPrimaryKey(String id);

    int insert(LikeArticle record);

    int insertSelective(LikeArticle record);

    LikeArticle selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LikeArticle record);

    int updateByPrimaryKey(LikeArticle record);

    @Select("select * from like_article where user_id=#{userId} and article_id=#{articleId}")
    LikeArticle selectByUserIdAndArticleId(@Param("userId") String userId,
                                           @Param("articleId") String articleId);
    //查询是否已经点过文章的赞
    @Select("select COUNT(*) from like_article where user_id=#{userId} and article_id=#{articleId}")
    int countAnswerZan(@Param("userId")String userId,
                       @Param("articleId")String articleId);
}