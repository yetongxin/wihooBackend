package com.yetx.dao;

import com.yetx.pojo.Answer;
import com.yetx.vo.AnswerDraftVO;
import com.yetx.vo.AnswerVO;
import com.yetx.vo.CommentVO;
import org.apache.ibatis.annotations.*;
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

    @Select("select id,content from answer where user_id=#{userId} and question_id=#{questionId}and status=0")
    @ResultMap("AnswerDraftVOMap")
    AnswerDraftVO getAnswerDraft(@Param("userId")String userId,@Param("questionId")String questionId);

    @Select("select a.*,u.nickname,u.avatar from answer a left join user u on u.id=a.user_id where a.id=#{id}")
    @ResultMap("AnswerVOMap")
    AnswerVO selectAnswerVOById(@Param("id") String id);
    //@Select("select * from answer where user_id = #{userId}")
    List<AnswerVO> selectByUserId(@Param("userId")String userId);



    //@Select("select * from answer where question_id = #{questionId} and status=1 order by create_time desc")
    List<AnswerVO> selectByQuestionIdOrderByTime(@Param("questionId") String questionId);

    //@Select("select * from answer where question_id = #{questionId} and status=1 order by like_counts desc")
    List<AnswerVO> selectByQuestionIdOrderByZan(@Param("questionId") String questionId);

    @Update("update answer set like_counts=like_counts+1 where id = #{answerId}")
    int addLikeCounts(@Param("answerId")String answerId);

    @Update("UPDATE answer SET like_counts=if(like_counts=0,0,like_counts-1) WHERE id=#{answerId}")
    int subLikeCounts(@Param("answerId")String answerId);

    @Update("UPDATE answer SET comment_counts=comment_counts+1 where id=#{answerId}")
    int addCommentCounts(@Param("answerId")String answerId);

    //@Select("SELECT a.* from answer a INNER JOIN collect_answer c on c.answer_id=a.id WHERE c.user_id=#{userId}")
    List<AnswerVO> selectCollectAnswer(@Param("userId")String userId);


}