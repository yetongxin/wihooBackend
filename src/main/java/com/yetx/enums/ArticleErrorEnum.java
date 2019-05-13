package com.yetx.enums;

public enum ArticleErrorEnum implements ErrorEnum{
    ARTICLEID_NOT_FIND(520,"文章id出错"),
    ARTICLE_UPLOAD_WITH_ERROR_OPENID(521,"文章上传的openid不匹配"),
    ARTICLEID_NULL(522,"文章id为空")

            ;
    private final Integer code;
    private final String msg;
    ArticleErrorEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
