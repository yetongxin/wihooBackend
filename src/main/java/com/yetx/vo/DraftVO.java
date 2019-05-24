package com.yetx.vo;

import java.util.Date;
import java.util.List;

public class DraftVO {

    private String id;

    private String title;

    private String cover;

    private String content;

    public DraftVO(String id, String title, String cover, String content, Date createTime) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.content = content;
    }

    public DraftVO() {
    }

    @Override
    public String toString() {
        return "DraftVO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                ", content='" + content + '\'' +
                '}';
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
