package com.yetx.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class User {
    private String id;

    @JsonIgnore
    private String openid;

    private String nickname;

    private String avatar;

    private Integer followCounts;

    private Integer fansCounts;

    private Integer collectCounts;

    private Integer likeCounts;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public User(String id, String openid, String nickname, String avatar, Integer followCounts, Integer fansCounts, Integer collectCounts, Integer likeCounts, Date createTime) {
        this.id = id;
        this.openid = openid;
        this.nickname = nickname;
        this.avatar = avatar;
        this.followCounts = followCounts;
        this.fansCounts = fansCounts;
        this.collectCounts = collectCounts;
        this.likeCounts = likeCounts;
        this.createTime = createTime;
    }

    public User() {
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public Integer getFollowCounts() {
        return followCounts;
    }

    public void setFollowCounts(Integer followCounts) {
        this.followCounts = followCounts;
    }

    public Integer getFansCounts() {
        return fansCounts;
    }

    public void setFansCounts(Integer fansCounts) {
        this.fansCounts = fansCounts;
    }

    public Integer getCollectCounts() {
        return collectCounts;
    }

    public void setCollectCounts(Integer collectCounts) {
        this.collectCounts = collectCounts;
    }

    public Integer getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(Integer likeCounts) {
        this.likeCounts = likeCounts;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", openid='" + openid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", followCounts=" + followCounts +
                ", fansCounts=" + fansCounts +
                ", collectCounts=" + collectCounts +
                ", likeCounts=" + likeCounts +
                ", createTime=" + createTime +
                '}';
    }
}