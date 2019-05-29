package com.yetx.dao;

import com.yetx.pojo.CollectArticle;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectArticleMapper {
    int deleteByPrimaryKey(String id);

    int insert(CollectArticle record);

    int insertSelective(CollectArticle record);

    CollectArticle selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CollectArticle record);

    int updateByPrimaryKey(CollectArticle record);

    CollectArticle checkIfCollect(@Param("userId") String userId, @Param("articleId") String articleId);

    //查询是否收藏过
    @Select("select COUNT(*) from collect_article where user_id=#{userId} and article_id=#{articleId}")
    int countArticleCollect(@Param("userId")String userId,
                            @Param("articleId")String articleId);
}