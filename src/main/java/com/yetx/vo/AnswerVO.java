package com.yetx.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class AnswerVO {
    private String id;

    @JsonIgnore
    private String questionId;

    private String userId;

    private String nickname;

    private String avatar;

    private String content;

    private Integer likeCounts;

    private Integer commentCounts;

    private Integer status;

    private Date createTime;

    public AnswerVO(String id, String questionId, String userId, String nickname, String avatar, String content, Integer likeCounts, Integer commentCounts, Integer status, Date createTime) {
        this.id = id;
        this.questionId = questionId;
        this.userId = userId;
        this.nickname = nickname;
        this.avatar = avatar;
        this.content = content;
        this.likeCounts = likeCounts;
        this.commentCounts = commentCounts;
        this.status = status;
        this.createTime = createTime;
    }

    public AnswerVO() {
    }

    @Override
    public String toString() {
        return "AnswerVO{" +
                "id='" + id + '\'' +
                ", questionId='" + questionId + '\'' +
                ", userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", content='" + content + '\'' +
                ", likeCounts=" + likeCounts +
                ", commentCounts=" + commentCounts +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
