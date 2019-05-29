package com.yetx.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AnswerDetailVO {
    @JsonProperty("question")
    private QuestionVO questionVO;
    @JsonProperty("answer")
    private AnswerVO answerVO;
    @JsonProperty("comments")
    private List<CommentVO> commentVOList;
    private Boolean hasZan;
    private Boolean hasCollect;
    private Boolean hasFollow;

    public AnswerDetailVO() {
    }

    public AnswerDetailVO(QuestionVO questionVO, AnswerVO answerVO, List<CommentVO> commentVOList, Boolean hasZan, Boolean hasCollect, Boolean hasFollow) {
        this.questionVO = questionVO;
        this.answerVO = answerVO;
        this.commentVOList = commentVOList;
        this.hasZan = hasZan;
        this.hasCollect = hasCollect;
        this.hasFollow = hasFollow;
    }

    public AnswerVO getAnswerVO() {
        return answerVO;
    }

    public void setAnswerVO(AnswerVO answerVO) {
        this.answerVO = answerVO;
    }

    public List<CommentVO> getCommentVOList() {
        return commentVOList;
    }

    public void setCommentVOList(List<CommentVO> commentVOList) {
        this.commentVOList = commentVOList;
    }

    public Boolean getHasZan() {
        return hasZan;
    }

    public void setHasZan(Boolean hasZan) {
        this.hasZan = hasZan;
    }

    public Boolean getHasCollect() {
        return hasCollect;
    }

    public void setHasCollect(Boolean hasCollect) {
        this.hasCollect = hasCollect;
    }

    public QuestionVO getQuestionVO() {
        return questionVO;
    }

    public void setQuestionVO(QuestionVO questionVO) {
        this.questionVO = questionVO;
    }

    public Boolean getHasFollow() {
        return hasFollow;
    }

    public void setHasFollow(Boolean hasFollow) {
        this.hasFollow = hasFollow;
    }
}

