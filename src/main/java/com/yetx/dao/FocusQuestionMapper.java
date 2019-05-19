package com.yetx.dao;

import com.yetx.pojo.FocusQuestion;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface FocusQuestionMapper {
    int deleteByPrimaryKey(String id);

    int insert(FocusQuestion record);

    int insertSelective(FocusQuestion record);

    FocusQuestion selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FocusQuestion record);

    int updateByPrimaryKey(FocusQuestion record);

    @Select("select COUNT(*) from focus_question where user_id=#{userId} and question_id=#{questionId}")
    int countUserFocusQues(@Param("userId")String userId,@Param("questionId")String questionId);

    @Delete("delete from focus_question where user_id=#{userId} and question_id=#{questionId}")
    int deleteUserFocusQues(@Param("userId")String userId,@Param("questionId")String questionId);

}