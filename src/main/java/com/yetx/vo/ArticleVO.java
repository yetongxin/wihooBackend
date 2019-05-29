package com.yetx.vo;

import com.yetx.pojo.Tag;

import java.util.Date;
import java.util.List;

public class ArticleVO {
    private String id;

    private String title;

    private String cover;

    private Integer status;

    private Integer likeCounts;

    private Integer collectCounts;

    private String content;

    private Date createTime;

    //private List<String> tags;
    private String userId;

    private String nickname;

    private String avatar;

    public ArticleVO() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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
        this.content = content;
    }

    @Override
    public String toString() {
        return "ArticleVO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                ", status=" + status +
                ", likeCounts=" + likeCounts +
                ", collectCounts=" + collectCounts +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
