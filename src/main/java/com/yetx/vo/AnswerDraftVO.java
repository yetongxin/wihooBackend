package com.yetx.vo;

public class AnswerDraftVO {
    private String id;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AnswerDraftVO() {
    }

    public AnswerDraftVO(String id, String content) {
        this.id = id;
        this.content = content;
    }
}
