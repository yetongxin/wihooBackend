package com.yetx.pojo;

import java.util.Date;

public class LikeArticle {
    private String id;

    private String articleId;

    private String userId;

    private Date createTime;

    public LikeArticle(String id, String articleId, String userId, Date createTime) {
        this.id = id;
        this.articleId = articleId;
        this.userId = userId;
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

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId == null ? null : articleId.trim();
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