package com.yetx.dao;

import com.yetx.constant.QuestionStatus;
import com.yetx.pojo.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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

    @Select("select * from question where status=1 order by create_time desc")
    List<Question> selectOrderByTime();

    @Select("select * from question where user_id=#{userId} and status="+ QuestionStatus.ONSHOW)
    List<Question> selectByUserId(@Param("userId")String userId);

    List<Question> selectByKeyWords(@Param("keywords")List<String> keywords);

    @Update("update question set focus_counts=focus_counts+1 where id=#{questionId}")
    int addFocusCounts(@Param("questionId") String questionId);

    @Update("update question set focus_counts=if(focus_counts=0,0,focus_counts-1) where id=#{questionId}")
    int subFocusCounts(@Param("questionId") String questionId);

}