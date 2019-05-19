package com.yetx.dto;

import java.util.Date;

public class QuestionDTO {

    //该字段可以为空，第一次上传时
    private String questionId;

    private String title;

    private String content;

    public QuestionDTO() {
    }

    public QuestionDTO(String questionId, String title, String content, Date createTime) {
        this.questionId = questionId;
        this.title = title;
        this.content = content;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "QuestionDTO{" +
                "questionId='" + questionId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
