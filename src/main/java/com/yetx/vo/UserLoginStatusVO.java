package com.yetx.vo;

public class UserLoginStatusVO {

    private String skey;

    private Boolean first;


    public UserLoginStatusVO(String skey, Boolean first) {
        this.skey = skey;
        this.first = first;
    }

    public UserLoginStatusVO() {
    }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    @Override
    public String toString() {
        return "UserLoginStatusVO{" +
                "skey='" + skey + '\'' +
                ", first=" + first +
                '}';
    }
}
