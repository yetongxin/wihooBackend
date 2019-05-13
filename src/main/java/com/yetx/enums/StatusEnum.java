package com.yetx.enums;

public enum StatusEnum implements ErrorEnum{
    SUCCESS(200,"成功"),
    ERROR(500,"请求出现问题");
    private final Integer code;
    private final String msg;
    StatusEnum(Integer code,String msg) {
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
