package com.yetx.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Comment {
    private String id;

    private String fromUid;

    private String toUid;

    private Integer likeCounts;

    @JsonIgnore
    private String parentId;

    @JsonIgnore
    private Integer parentType;

    private Date createTime;

    private String content;

    public Comment(String id, String fromUid, String toUid, Integer likeCounts, String parentId, Integer parentType, Date createTime, String content) {
        this.id = id;
        this.fromUid = fromUid;
        this.toUid = toUid;
        this.likeCounts = likeCounts;
        this.parentId = parentId;
        this.parentType = parentType;
        this.createTime = createTime;
        this.content = content;
    }

    public Comment() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Integer getParentType() {
        return parentType;
    }

    public void setParentType(Integer parentType) {
        this.parentType = parentType;
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
        return "Comment{" +
                "id='" + id + '\'' +
                ", fromUid='" + fromUid + '\'' +
                ", toUid='" + toUid + '\'' +
                ", likeCounts=" + likeCounts +
                ", parentId='" + parentId + '\'' +
                ", parentType=" + parentType +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                '}';
    }
}