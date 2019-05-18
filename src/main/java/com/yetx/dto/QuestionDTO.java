package com.yetx.dto;

import java.util.Date;

public class QuestionDTO {

    //该字段可以为空，第一次上传时
    private String questionId;

    private String title;

    private String content;

    private Date createTime;

    public QuestionDTO() {
    }

    public QuestionDTO(String questionId, String title, String content, Date createTime) {
        this.questionId = questionId;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "QuestionDTO{" +
                "id='" + questionId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
