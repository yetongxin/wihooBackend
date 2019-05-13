package com.yetx.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yetx.pojo.Comment;

import java.util.Date;
import java.util.List;

public class CommentVO {
    private String id;

    private String fromUid;

    private Integer likeCounts;

    private String fromNickname;


    private Date createTime;

    private String content;

    private List<Comment> subComments;

    public CommentVO() {
    }

    @Override
    public String toString() {
        return "CommentVO{" +
                "id='" + id + '\'' +
                ", fromUid='" + fromUid + '\'' +
                ", likeCounts=" + likeCounts +
                ", fromNickname='" + fromNickname + '\'' +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                ", subComments=" + subComments +
                '}';
    }

    public String getFromNickname() {
        return fromNickname;
    }

    public void setFromNickname(String fromNickname) {
        this.fromNickname = fromNickname;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromUid() {
        return fromUid;
    }

    public void setFromUid(String fromUid) {
        this.fromUid = fromUid;
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
        this.content = content;
    }

    public List<Comment> getSubComments() {
        return subComments;
    }

    public void setSubComments(List<Comment> subComments) {
        this.subComments = subComments;
    }
}
