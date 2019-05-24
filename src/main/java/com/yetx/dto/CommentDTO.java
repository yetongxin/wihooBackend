package com.yetx.dto;


public class CommentDTO {
    //这里的id可能是answerid,也可能是answer_comment_id，也可能是articleId
    private String id;

    private Integer type;

    private String toUid;

    private String content;

    public CommentDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getToUid() {
        return toUid;
    }

    public void setToUid(String toUid) {
        this.toUid = toUid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", toUid='" + toUid + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
