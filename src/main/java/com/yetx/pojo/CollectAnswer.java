package com.yetx.pojo;

public class CollectAnswer {
    private String id;

    private String userId;

    private String answerId;

    public CollectAnswer(String id, String userId, String answerId) {
        this.id = id;
        this.userId = userId;
        this.answerId = answerId;
    }

    public CollectAnswer() {
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

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId == null ? null : answerId.trim();
    }
}