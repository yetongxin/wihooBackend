package com.yetx.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Tag {
    private String id;

    private String articleId;

    private String content;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Tag(String id, String articleId, String content, Date createTime) {
        this.id = id;
        this.articleId = articleId;
        this.content = content;
        this.createTime = createTime;
    }

    public Tag() {
        super();
    }

    public Tag(String id, String articleId, String content) {
        this.id = id;
        this.articleId = articleId;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}