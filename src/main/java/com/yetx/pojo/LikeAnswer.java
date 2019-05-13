package com.yetx.pojo;

import java.util.Date;

public class LikeAnswer {
    private String id;

    private String answerId;

    private String userId;

    private Date createTime;

    public LikeAnswer(String id, String answerId, String userId, Date createTime) {
        this.id = id;
        this.answerId = answerId;
        this.userId = userId;
        this.createTime = createTime;
    }

    public LikeAnswer() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId == null ? null : answerId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}