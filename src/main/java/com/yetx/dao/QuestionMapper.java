package com.yetx.dao;

import com.yetx.constant.QuestionStatus;
import com.yetx.pojo.Question;
import com.yetx.vo.QuestionVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface QuestionMapper {
    int deleteByPrimaryKey(String id);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKeyWithBLOBs(Question record);

    int updateByPrimaryKey(Question record);


    @Select("select q.*,u.nickname,u.avatar from question q left join user u on u.id=q.user_id where status=1 and q.id=#{id}")
    @ResultMap("QuestionVO")
    QuestionVO selectVOByPrimaryKey(@Param(("id")) String id);

    //    @Select("select * from question where status=1 order by create_time desc")
    @Select("select q.*,u.nickname,u.avatar from question q left join user u on u.id=q.user_id where status=1 order by create_time desc")
    @ResultMap("QuestionVO")
    List<QuestionVO> selectOrderByTime();

    @Select("select q.*,u.nickname,u.avatar from question q left join user u on u.id=q.user_id where status=1 order by ans_counts desc")
    @ResultMap("QuestionVO")
    List<QuestionVO> selctOrderByAnsCounts();

    @Select("select q.*,u.nickname,u.avatar from question q left join user u on u.id=q.user_id where user_id=#{userId} and status="+ QuestionStatus.ONSHOW)
    @ResultMap("QuestionVO")
    List<QuestionVO> selectByUserId(@Param("userId")String userId);

    List<QuestionVO> selectByKeyWords(@Param("keywords")List<String> keywords);

    @Update("update question set focus_counts=focus_counts+1 where id=#{questionId}")
    int addFocusCounts(@Param("questionId") String questionId);

    @Update("update question set focus_counts=if(focus_counts=0,0,focus_counts-1) where id=#{questionId}")
    int subFocusCounts(@Param("questionId") String questionId);

    @Update("update question set ans_counts=ans_counts+1 where id=#{questionId}")
    int addAnswerCounts(@Param("questionId") String questionId);

    @Update("update question set ans_counts=if(ans_counts=0,0,ans_counts-1) where id=#{questionId}")
    int subAnswerCounts(@Param("questionId") String questionId);


    @Select("select q.* from question q inner join focus_question f on f.question_id=q.id left join user u on u.id=q.user_id where f.user_id=#{userId}")
    @ResultMap("QuestionVO")
    List<QuestionVO> selectFocusQuestion(@Param("userId") String userId);

    //不用的
    List<Question> selectByIdSet(@Param("idSet") Set<String> idSet);

}