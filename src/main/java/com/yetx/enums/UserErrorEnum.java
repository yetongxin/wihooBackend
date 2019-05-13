package com.yetx.enums;

public enum UserErrorEnum implements ErrorEnum{
    NOT_LOG_IN(511,"未登录"),
    TOKEN_NOT_FIND(512,"登录过期未登录"),
    UPDATE_AVATAR_ERROR(513,"头像更新出错")
    ;
    private final Integer code;
    private final String msg;
    UserErrorEnum(Integer code, String msg) {
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
