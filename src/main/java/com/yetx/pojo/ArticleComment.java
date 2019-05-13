package com.yetx.pojo;

import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class ArticleComment {
    private String id;

    private String articleId;

    private String fromUid;

    private String toUid;

    private Integer likeCounts;

    private Date createTime;

    private String content;

    public ArticleComment(String id, String articleId, String fromUid, String toUid, Integer likeCounts, Date createTime, String content) {
        this.id = id;
        this.articleId = articleId;
        this.fromUid = fromUid;
        this.toUid = toUid;
        this.likeCounts = likeCounts;
        this.createTime = createTime;
        this.content = content;
    }

    public ArticleComment() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId == null ? null : articleId.trim();
    }

    public String getFromUid() {
        return fromUid;
    }

    public void setFromUid(String fromUid) {
        this.fromUid = fromUid == null ? null : fromUid.trim();
    }

    public String getToUid() {
        return toUid;
    }

    public void setToUid(String toUid) {
        this.toUid = toUid == null ? null : toUid.trim();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}