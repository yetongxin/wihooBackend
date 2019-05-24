package com.yetx.enums;

public enum ArticleErrorEnum implements ErrorEnum{
    ARTICLEID_NOT_FIND(520,"文章id出错"),
    ARTICLE_UPLOAD_WITH_ERROR_USERID(521,"文章上传的userId不匹配"),
    ARTICLEID_NULL(522,"文章id为空"),
    UPLOAD_ARTICLEID_NULL(523,"上传的id为空"),
    UPLOAD_PARAMS_ERROR(524,"上传参数错误")
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
