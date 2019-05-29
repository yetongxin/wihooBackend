package com.yetx.dao;

import com.yetx.pojo.Article;
import com.yetx.vo.ArticleVO;
import com.yetx.vo.DraftVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper {
    int deleteByPrimaryKey(String id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(String id);



    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    @Select("select * from article where user_Id=#{userId}")
    List<Article> selectOtherUserArticle(@Param("userId")String userId);

    List<ArticleVO> selectAllByUserIdPaged(@Param("userId") String userId);

    List<ArticleVO> selectAllByUserId(@Param("userId") String userId);

    //查看用户的草稿
    DraftVO selectDraftByUserId(@Param("userId") String userId);

    List<ArticleVO> selectByPopularity();
    List<ArticleVO> selectByTime();


    ArticleVO selectVOByArticleId(@Param("articleId")String articleId);

}