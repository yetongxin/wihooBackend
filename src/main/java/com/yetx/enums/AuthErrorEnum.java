package com.yetx.enums;

public enum AuthErrorEnum implements ErrorEnum{
    TOKEN_NOT_FIND(501,"登录过期或未登录"),
    NOT_LOG_IN(502,"未登录"),
    OPENID_NOT_FIND(503,"登录已过期，请重新登录")
    ;
    private final Integer code;
    private final String msg;
    AuthErrorEnum(Integer code,String msg) {
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
