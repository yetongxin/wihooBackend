package com.yetx.enums;

public enum CommentErrorEnum implements ErrorEnum {
    COMMENT_NULL(560,"评论传输出错null"),
    COMMENT_ID_NULL(561,"评论id为Null")
    ;

    private final Integer code;
    private final String msg;
    CommentErrorEnum(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
