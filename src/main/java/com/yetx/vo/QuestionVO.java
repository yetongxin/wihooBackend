package com.yetx.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class QuestionVO {
    private String id;

    private String userId;

    private String nickname;

    private String avatar;

    private String title;

    private String content;

    private Integer focusCounts;

    private Integer ansCounts;

    @JsonIgnore
    private Integer status;

    private Date createTime;

    public QuestionVO() {
    }

    public QuestionVO(String id, String userId, String nickname, String avatar, String title, String content, Integer focusCounts, Integer ansCounts, Integer status, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.avatar = avatar;
        this.title = title;
        this.content = content;
        this.focusCounts = focusCounts;
        this.ansCounts = ansCounts;
        this.status = status;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
