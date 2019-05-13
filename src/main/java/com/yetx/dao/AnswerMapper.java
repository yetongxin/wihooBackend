package com.yetx.dao;

import com.yetx.pojo.Answer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerMapper {
    int deleteByPrimaryKey(String id);

    int insert(Answer record);

    int insertSelective(Answer record);

    Answer selectByPrimaryKey(String id);

    List<Answer> selectByOpenid(String openid);

    int updateByPrimaryKeySelective(Answer record);

    int updateByPrimaryKeyWithBLOBs(Answer record);

    int updateByPrimaryKey(Answer record);
}