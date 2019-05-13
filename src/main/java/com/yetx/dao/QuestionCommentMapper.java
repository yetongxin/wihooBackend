package com.yetx.dao;

import com.yetx.pojo.QuestionComment;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionCommentMapper {
    int deleteByPrimaryKey(String id);

    int insert(QuestionComment record);

    int insertSelective(QuestionComment record);

    QuestionComment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QuestionComment record);

    int updateByPrimaryKeyWithBLOBs(QuestionComment record);

    int updateByPrimaryKey(QuestionComment record);
}