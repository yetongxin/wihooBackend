package com.yetx.dao;

import com.yetx.pojo.ArticleComment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleCommentMapper {
    int deleteByPrimaryKey(String id);

    int insert(ArticleComment record);

    int insertSelective(ArticleComment record);

    ArticleComment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ArticleComment record);

    int updateByPrimaryKeyWithBLOBs(ArticleComment record);

    int updateByPrimaryKey(ArticleComment record);

    List<ArticleComment> selectByArticleId(String articleId);
}