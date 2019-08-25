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

    private String fromAvatar;

    private Date createTime;

    private String content;

    private Boolean hasZan=false;

    private List<SubCommentVO> subComments;

    public CommentVO() {
    }

    @Override
    public String toString() {
        return "CommentVO{" +
                "id='" + id + '\'' +
                ", fromUid='" + fromUid + '\'' +
                ", likeCounts=" + likeCounts +
                ", fromNickname='" + fromNickname + '\'' +
                ", fromAvatar='" + fromAvatar + '\'' +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                ", hasZan=" + hasZan +
                ", subComments=" + subComments +
                '}';
    }

    public Boolean getHasZan() {
        return hasZan;
    }

    public void setHasZan(Boolean hasZan) {
        this.hasZan = hasZan;
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

    public List<SubCommentVO> getSubComments() {
        return subComments;
    }

    public void setSubComments(List<SubCommentVO> subComments) {
        this.subComments = subComments;
    }

    public String getFromAvatar() {
        return fromAvatar;
    }

    public void setFromAvatar(String fromAvatar) {
        this.fromAvatar = fromAvatar;
    }
}
