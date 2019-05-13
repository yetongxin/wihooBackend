package com.yetx.pojo;

public class FocusQuestion {
    private String id;

    private String userId;

    private String questionId;

    public FocusQuestion(String id, String userId, String questionId) {
        this.id = id;
        this.userId = userId;
        this.questionId = questionId;
    }

    public FocusQuestion() {
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

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId == null ? null : questionId.trim();
    }
}