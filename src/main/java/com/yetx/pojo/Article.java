package com.yetx.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Article {
    private String id;

    @JsonIgnore
    private String userId;

    private String title;

    private String content;

    private String cover;

    private Integer status;

    private Integer likeCounts;

    private Integer collectCounts;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    public Article(String id, String openid, String title, String cover, Integer status, Integer likeCounts, Integer collectCounts, Date createTime, String content) {
        this.id = id;
        this.userId = openid;
        this.title = title;
        this.cover = cover;
        this.status = status;
        this.likeCounts = likeCounts;
        this.collectCounts = collectCounts;
        this.createTime = createTime;
        this.content = content;
    }

    public Article() {
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
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(Integer likeCounts) {
        this.likeCounts = likeCounts;
    }

    public Integer getCollectCounts() {
        return collectCounts;
    }

    public void setCollectCounts(Integer collectCounts) {
        this.collectCounts = collectCounts;
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
        return "Article{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                ", status=" + status +
                ", likeCounts=" + likeCounts +
                ", collectCounts=" + collectCounts +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                '}';
    }
}