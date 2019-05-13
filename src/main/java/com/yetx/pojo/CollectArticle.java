package com.yetx.pojo;

public class CollectArticle {
    private String id;

    private String userId;

    private String articleId;

    public CollectArticle(String id, String userId, String articleId) {
        this.id = id;
        this.userId = userId;
        this.articleId = articleId;
    }

    public CollectArticle() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId == null ? null : articleId.trim();
    }

    @Override
    public String toString() {
        return "CollectArticle{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", articleId='" + articleId + '\'' +
                '}';
    }
}