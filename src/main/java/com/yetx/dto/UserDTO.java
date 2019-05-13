package com.yetx.dto;

import com.yetx.pojo.Answer;
import com.yetx.pojo.Article;
import com.yetx.pojo.Question;
import com.yetx.pojo.User;

import java.util.Date;
import java.util.List;

public class UserDTO {
    private String id;

    private String openid;

    private String nickname;

    private String avatar;

    private Integer followCounts;

    private Integer fansCounts;

    private Integer collectCounts;

    private Integer likeCounts;

    private List<OtherUserDTO> followUsers;

    private Date createTime;



    public UserDTO() {
    }


    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + id + '\'' +
                ", openid='" + openid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", followCounts=" + followCounts +
                ", fansCounts=" + fansCounts +
                ", collectCounts=" + collectCounts +
                ", likeCounts=" + likeCounts +
                ", followUser=" + followUsers +
                ", createTime=" + createTime +
                '}';
    }

    public List<OtherUserDTO> getFollowUsers() {
        return followUsers;
    }

    public void setFollowUser(List<OtherUserDTO> followUser) {
        this.followUsers = followUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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
}
