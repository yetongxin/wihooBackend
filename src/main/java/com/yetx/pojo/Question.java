package com.yetx.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Question {
    private String id;

    @JsonIgnore
    private String openid;

    private String title;

    private Integer focusCounts;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String content;

    public Question(String id, String openid, String title, Integer focusCounts, Date createTime, String content) {
        this.id = id;
        this.openid = openid;
        this.title = title;
        this.focusCounts = focusCounts;
        this.createTime = createTime;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", openid='" + openid + '\'' +
                ", title='" + title + '\'' +
                ", focusCounts=" + focusCounts +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                '}';
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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
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
}