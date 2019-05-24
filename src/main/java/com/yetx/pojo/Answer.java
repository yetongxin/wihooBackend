package com.yetx.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Answer {
    private String id;

    @JsonIgnore
    private String questionId;

    private String userId;

    private String content;

    private Integer likeCounts;

    private Integer commentCounts;

    private Integer status;

    private Date createTime;


    public Answer(String id, String questionId, String userId, Integer likeCounts, Integer commentCounts, Integer status, Date createTime, String content) {
        this.id = id;
        this.questionId = questionId;
        this.userId = userId;
        this.likeCounts = likeCounts;
        this.commentCounts = commentCounts;
        this.status = status;
        this.createTime = createTime;
        this.content = content;
    }

    public Answer() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId == null ? null : questionId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(Integer likeCounts) {
        this.likeCounts = likeCounts;
    }

    public Integer getCommentCounts() {
        return commentCounts;
    }

    public void setCommentCounts(Integer commentCounts) {
        this.commentCounts = commentCounts;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}