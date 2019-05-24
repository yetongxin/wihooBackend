package com.yetx.dao;

import com.yetx.pojo.Comment;
import com.yetx.vo.CommentVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    int deleteByPrimaryKey(String id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKeyWithBLOBs(Comment record);

    int updateByPrimaryKey(Comment record);

    @Select("select * from comment where parent_type=2 and parent_id=#{articleId}")
    List<Comment> selectCommentsByArticleId(@Param("articleId") String articleId);

    @Select("select * from comment where parent_type=1 and parent_id=#{commentId}")
    List<Comment> selectCommentsByCommentId(@Param("commentId") String commentId);

    List<CommentVO> selectCommentsvoByArticleId(String articleId);

    @Delete("delete from comment where parent_type=1 and parent_id=#{commentId}")
    int deleteSubComment(@Param("commentId")String commentId);

    List<CommentVO> selectCommentsVOByAnswerId(String answerId);

    @Update("update comment set content=\"该评论已删除\",parent_type=5 where id=#{commentId}")
    int deleteComment(@Param("commentId")String commentId);

    @Update("update comment set like_counts=like_counts+1 where id=#{commentId}")
    int addCommentZan(@Param("commentId")String commentId);

    @Update("update comment set like_counts=if(like_counts=0,0,like_counts-1) where id=#{commentId}")
    int subCommentZan(@Param("commentId")String commentId);

}