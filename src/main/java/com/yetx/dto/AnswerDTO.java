package com.yetx.dto;

import java.util.Date;

public class AnswerDTO {

    private String answerId;

    private String questionId;

    private String content;

    private Integer status;

    public AnswerDTO() {
    }


    public AnswerDTO(String answerId, String questionId, String userId, String content, Integer status) {
        this.answerId = answerId;
        this.questionId = questionId;
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
                ", content='" + content + '\'' +
                ", status=" + status +
                '}';
    }

}
