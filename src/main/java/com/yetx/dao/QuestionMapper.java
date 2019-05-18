package com.yetx.dao;

import com.yetx.pojo.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionMapper {
    int deleteByPrimaryKey(String id);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKeyWithBLOBs(Question record);

    int updateByPrimaryKey(Question record);

    @Select("select * from question order by create_time desc")
    List<Question> selectOrderByTime();

    @Select("select * from question where user_id=userId")
    List<Question> selectByUserId(@Param("userId")String userId);

    List<Question> selectByKeyWords(@Param("keywords")List<String> keywords);

}