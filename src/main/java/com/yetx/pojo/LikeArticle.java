package com.yetx.pojo;

import java.util.Date;

public class LikeArticle {
    private String id;

    private String likeTypeId;

    private String userId;

    private Integer type;

    private Date createTime;

    public LikeArticle(String id, String likeTypeId, String userId, Integer type, Date createTime) {
        this.id = id;
        this.likeTypeId = likeTypeId;
        this.userId = userId;
        this.type = type;
        this.createTime = createTime;
    }

    public LikeArticle() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLikeTypeId() {
        return likeTypeId;
    }

    public void setLikeTypeId(String likeTypeId) {
        this.likeTypeId = likeTypeId == null ? null : likeTypeId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}