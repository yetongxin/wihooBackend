package com.yetx.vo;

public class UserInfoVO {

    private String skey;

    private String nickname;

    private String avatar;

    private Integer followCounts;

    private Integer fansCounts;

    private Integer collectCounts;

    private Integer likeCounts;

    public UserInfoVO() {
    }

    public UserInfoVO(String skey, String nickname, String avatar, Integer followCounts,
                      Integer fansCounts, Integer collectCounts, Integer likeCounts) {
        this.skey = skey;
        this.nickname = nickname;
        this.avatar = avatar;
        this.followCounts = followCounts;
        this.fansCounts = fansCounts;
        this.collectCounts = collectCounts;
        this.likeCounts = likeCounts;
    }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
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
}
