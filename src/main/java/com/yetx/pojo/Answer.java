package com.yetx.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Answer {
    private String id;

    private String questionId;

    @JsonIgnore
    private String openid;

    private Integer likeCounts;

    private Integer commentCounts;

    private Date createTime;

    private String content;

    public Answer(String id, String questionId, String openid, Integer likeCounts, Integer commentCounts, Date createTime, String content) {
        this.id = id;
        this.questionId = questionId;
        this.openid = openid;
        this.likeCounts = likeCounts;
        this.commentCounts = commentCounts;
        this.createTime = createTime;
        this.content = content;
    }

    public Answer() {
        super();
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id='" + id + '\'' +
                ", questionId='" + questionId + '\'' +
                ", openid='" + openid + '\'' +
                ", likeCounts=" + likeCounts +
                ", commentCounts=" + commentCounts +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                '}';
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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
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