package com.yetx.pojo;

import java.util.Date;

public class Question {
    private String id;

    private String userId;

    private String title;

    private Integer focusCounts;

    private Integer ansCounts;

    private Date createTime;

    private String content;

    public Question(String id, String userId, String title, Integer focusCounts, Integer ansCounts, Date createTime, String content) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.focusCounts = focusCounts;
        this.ansCounts = ansCounts;
        this.createTime = createTime;
        this.content = content;
    }

    public Question() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getFocusCounts() {
        return focusCounts;
    }

    public void setFocusCounts(Integer focusCounts) {
        this.focusCounts = focusCounts;
    }

    public Integer getAnsCounts() {
        return ansCounts;
    }

    public void setAnsCounts(Integer ansCounts) {
        this.ansCounts = ansCounts;
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

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", focusCounts=" + focusCounts +
                ", ansCounts=" + ansCounts +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                '}';
    }
}