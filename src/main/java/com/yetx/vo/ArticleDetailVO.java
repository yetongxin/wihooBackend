package com.yetx.vo;

import java.util.Date;
import java.util.List;

public class ArticleDetailVO {
    private String id;

    private String title;

    private String cover;

    private Integer likeCounts;

    private Integer collectCounts;

    private String content;

    private Date createTime;

    private String nickname;

    private String avatar;

    private Boolean hasZan;

    private Boolean hasCollect;

    private Boolean hasFollow;

    private List<CommentVO> comments;

    public ArticleDetailVO(ArticleVO articleVO){
        this.id = articleVO.getId();
        this.title = articleVO.getTitle();
        this.cover = articleVO.getCover();
        this.likeCounts = articleVO.getLikeCounts();
        this.collectCounts = articleVO.getCollectCounts();
        this.content = articleVO.getContent();
        this.createTime = articleVO.getCreateTime();
        this.nickname = articleVO.getNickname();
        this.avatar = articleVO.getAvatar();
        this.comments = null;
        this.hasZan = false;
        this.hasCollect = false;
        this.hasFollow = false;
    }

    public ArticleDetailVO() {
    }

    public ArticleDetailVO(String id, String title, String cover, Integer likeCounts, Integer collectCounts, String content, Date createTime, String nickname, String avatar, Boolean hasZan, Boolean hasCollect, Boolean hasFollow, List<CommentVO> comments) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.likeCounts = likeCounts;
        this.collectCounts = collectCounts;
        this.content = content;
        this.createTime = createTime;
        this.nickname = nickname;
        this.avatar = avatar;
        this.hasZan = hasZan;
        this.hasCollect = hasCollect;
        this.hasFollow = hasFollow;
        this.comments = comments;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public List<CommentVO> getComments() {
        return comments;
    }

    public void setComments(List<CommentVO> comments) {
        this.comments = comments;
    }

    public Boolean getHasCollect() {
        return hasCollect;
    }

    public void setHasCollect(Boolean hasCollect) {
        this.hasCollect = hasCollect;
    }

    public Boolean getHasZan() {
        return hasZan;
    }

    public void setHasZan(Boolean hasZan) {
        this.hasZan = hasZan;
    }

    public void setHasFollow(Boolean hasFollow) {
        this.hasFollow = hasFollow;
    }

    public Boolean getHasFollow() {
        return hasFollow;
    }
}
