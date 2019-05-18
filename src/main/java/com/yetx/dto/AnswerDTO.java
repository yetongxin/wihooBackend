package com.yetx.dto;

import java.util.Date;

public class AnswerDTO {

    private String answerId;

    private String questionId;

    private String userId;

    private String content;

    private Integer status;

    public AnswerDTO() {
    }


    public AnswerDTO(String answerId, String questionId, String userId, String content, Integer status) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.userId = userId;
        this.content = content;
        this.status = status;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AnswerDTO{" +
                "answerId='" + answerId + '\'' +
                ", questionId='" + questionId + '\'' +
                ", userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                '}';
    }

}
