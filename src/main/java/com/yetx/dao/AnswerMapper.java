package com.yetx.dao;

import com.yetx.pojo.Answer;
import com.yetx.vo.CommentVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AnswerMapper {
    int deleteByPrimaryKey(String id);

    int insert(Answer record);

    int insertSelective(Answer record);

    Answer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Answer record);

    int updateByPrimaryKeyWithBLOBs(Answer record);

    int updateByPrimaryKey(Answer record);

    @Select("select * from answer where user_id = #{userId}")
    List<Answer> selectByUserId(@Param("userId")String userId);

    @Select("select * from answer where question_id = #{questionId} order by create_time desc")
    List<Answer> selectByQuestionIdOrderByTime(@Param("questionId") String questionId);

    @Select("select * from answer where question_id = #{questionId} order by like_counts desc")
    List<Answer> selectByQuestionIdOrderByZan(@Param("questionId") String questionId);

    @Update("update answer set like_counts=like_counts+1 where id = #{answerId}")
    int addLikeCounts(@Param("answerId")String answerId);

    @Update("UPDATE answer SET like_counts=if(like_counts=0,0,like_counts-1) WHERE id=#{answerId}")
    int subLikeCounts(@Param("answerId")String answerId);

    @Update("UPDATE answer SET comment_counts=comment_counts+1 where id=#{answerId}")
    int addCommentCounts(@Param("answerId")String answerId);

}