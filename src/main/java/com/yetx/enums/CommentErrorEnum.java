package com.yetx.enums;

public enum CommentErrorEnum implements ErrorEnum {
    COMMENT_NULL(560,"评论传输出错null")
    ;

    private final Integer code;
    private final String msg;
    CommentErrorEnum(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }
    @Override
    public Integer getCode() {
        return null;
    }

    @Override
    public String getMsg() {
        return null;
    }
}
