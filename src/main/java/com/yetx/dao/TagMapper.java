package com.yetx.dao;

import com.yetx.pojo.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagMapper {
    int deleteByPrimaryKey(String id);

    int insert(Tag record);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);

    int deleteTagByArticleId(@Param("articleId")String articleId);
}