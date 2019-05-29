package com.yetx.dao;

import com.yetx.constant.ZanType;
import com.yetx.pojo.LikeArticle;
import org.apache.ibatis.annotations.Delete;
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

    @Select("select * from like_article where user_id=#{userId} and like_type_id=#{articleId} and type=1")
    LikeArticle selectByUserIdAndArticleId(@Param("userId") String userId,
                                           @Param("articleId") String articleId);


    //查询是否已经有文章的赞
    @Select("select COUNT(*) from like_article where user_id=#{userId} and like_type_id=#{likeId} and type=1")
    int countArticleZan(@Param("userId")String userId,
                       @Param("likeId")String likeId);
    //删除文章的赞
    @Delete("delete from like_article where user_id=#{userId} and like_type_id=#{likeId} and type=1")
    int deleteArticleZan(@Param("userId")String userId,
                        @Param("likeId")String likeId);
    //查询是否已经有文章的评论的赞
    @Select("select COUNT(*) from like_article where user_id=#{userId} and like_type_id=#{likeId} and type=2")
    int countCommentZan(@Param("userId")String userId,
                        @Param("likeId")String likeId);
    //删除文章的评论的赞
    @Delete("delete from like_article where user_id=#{userId} and like_type_id=#{likeId} and type=2")
    int deleteCommentZan(@Param("userId")String userId,
                         @Param("likeId")String likeId);

}