package com.yetx.dao;

import com.yetx.constant.CommentType;
import com.yetx.constant.ZanType;
import com.yetx.pojo.LikeAnswer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeAnswerMapper {
    int deleteByPrimaryKey(String id);

    int insert(LikeAnswer record);

    int insertSelective(LikeAnswer record);

    LikeAnswer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LikeAnswer record);

    int updateByPrimaryKey(LikeAnswer record);

    //查询是否已经有回答的赞
    @Select("select COUNT(*) from like_answer where user_id=#{userId} and like_type_id=#{likeId} and type="+ZanType.ANS_ZAN)
    int countAnswerZan(@Param("userId")String userId,
                        @Param("likeId")String likeId);
    //删除回答的赞
    @Delete("delete from like_answer where user_id=#{userId} and like_type_id=#{likeId} and type="+ZanType.ANS_ZAN)
    int deleteAnswerZan(@Param("userId")String userId,
                        @Param("likeId")String likeId);
    //查询是否已经有回答的评论的赞
    @Select("select COUNT(*) from like_answer where user_id=#{userId} and like_type_id=#{likeId} and type="+ZanType.ANS_COMZAN)
    int countCommentZan(@Param("userId")String userId,
                        @Param("likeId")String likeId);
    //删除回答的评论的赞
    @Delete("delete from like_answer where user_id=#{userId} and like_type_id=#{likeId} and type="+ZanType.ANS_COMZAN)
    int deleteCommentZan(@Param("userId")String userId,
                         @Param("likeId")String likeId);


}