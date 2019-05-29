package com.yetx.vo;


import com.yetx.pojo.Answer;
import com.yetx.pojo.Article;
import com.yetx.pojo.User;

import java.util.Date;
import java.util.List;


public class OtherUserVO {
    private String id;

    private String nickname;

    private String avatar;

    private String bgimage;

    private Integer followCounts;

    private Integer fansCounts;

    private Integer collectCounts;

    private Integer likeCounts;

    private List<Article> ofArticles;

    private List<AnswerVO> ofAnswers;

    public OtherUserVO(User user){
        this.id=user.getId();
        this.nickname = user.getNickname();
        this.avatar = user.getAvatar();
        this.bgimage = user.getBgimage();
        this.followCounts = user.getFollowCounts();
        this.fansCounts = user.getFansCounts();
        this.collectCounts = user.getCollectCounts();
        this.likeCounts = user.getLikeCounts();
        this.ofArticles = null;
        this.ofAnswers = null;
    }

    public OtherUserVO() {
    }

    public OtherUserVO(String id, String nickname, String avatar, String bgimage, Integer followCounts, Integer fansCounts, Integer collectCounts, Integer likeCounts, List<Article> ofArticles, List<AnswerVO> ofAnswers) {
        this.id = id;
        this.nickname = nickname;
        this.avatar = avatar;
        this.bgimage = bgimage;
        this.followCounts = followCounts;
        this.fansCounts = fansCounts;
        this.collectCounts = collectCounts;
        this.likeCounts = likeCounts;
        this.ofArticles = ofArticles;
        this.ofAnswers = ofAnswers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBgimage() {
        return bgimage;
    }

    public void setBgimage(String bgimage) {
        this.bgimage = bgimage;
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

    public List<Article> getOfArticles() {
        return ofArticles;
    }

    public void setOfArticles(List<Article> ofArticles) {
        this.ofArticles = ofArticles;
    }

    public List<AnswerVO> getOfAnswers() {
        return ofAnswers;
    }

    public void setOfAnswers(List<AnswerVO> ofAnswers) {
        this.ofAnswers = ofAnswers;
    }
}
