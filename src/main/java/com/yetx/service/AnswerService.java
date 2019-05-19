package com.yetx.service;

import com.yetx.dto.AnswerDTO;
import com.yetx.dto.CommentDTO;
import com.yetx.pojo.Answer;
import com.yetx.vo.CommentVO;
import com.yetx.vo.PageVO;

import java.util.List;

public interface AnswerService {


    //按点赞数排序展示回答
    public PageVO findAllAnswersOrderByZan(String questionId, Integer staPage, Integer pageSize);

    //按时间排序展示回答
    public PageVO findAllAnswersOrderByTime(String questionId, Integer staPage, Integer pageSize);

    //展示所有回答
    public List<CommentVO> findCommentsByAnswerId(String answerId);
    /**
     * 上传修改回答，这里的upload仅限于第一次上传与修改状态或者内容，允许传部分空值
     * @param token
     * @param answerDTO 上传的answer信息，根据其中属性是否空进行更新操作
     * @return 插入或更新后数据库中的answer
     */
    public Answer uploadAnswer(String token, AnswerDTO answerDTO);

    public Answer updateAnswer(String token,AnswerDTO answerDTO);
    /**
     * 删除回答
     * @param token
     * @param answerId
     * @return
     */
    public boolean deleteAnswer(String token,String answerId);
    /**
     * 点赞回答
     * @param token
     * @param answerId
     * @return 数据库中赞的个数
     */
    public Integer zanAnswer(String token, String answerId);

    /**
     * 取消点赞回答
     * @param token
     * @param answerId
     * @return 数据库中赞的个数
     */
    public Integer diszanAnswer(String token, String answerId);
    /**
     * 评论回答,不允许修改
     * @param token
     * @param commentDTO comment具体信息
     * @return comment的id
     */
    public String commentAnswer(String token, CommentDTO commentDTO);
    /**
     * 删除评论，如果删除的是回答的评论，需要更新下answer的comment_counts字段
     * @param token
     * @param commentId
     * @return
     */
    public Boolean deleteComment(String token,String commentId);

    /**
     * 点赞回答的评论
     * @param token
     * @param commentId
     * @return 数据库中对应评论的赞数
     */
    public Integer zanAnswerComment(String token, String commentId);
    /**
     * 取消点赞回答
     * @param token
     * @param commentId
     * @return 数据库中对应评论的赞数
     */
    public Integer diszanAnswerComment(String token, String commentId);

}
