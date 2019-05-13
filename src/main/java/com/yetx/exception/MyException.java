package com.yetx.exception;

import com.yetx.enums.ErrorEnum;

public class MyException extends RuntimeException {
    private Integer code;
    public MyException(String msg,Integer code){
        super(msg);
        this.code = code;
    }
    public MyException(ErrorEnum errorEnum){
        super(errorEnum.getMsg());
        this.code = errorEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
