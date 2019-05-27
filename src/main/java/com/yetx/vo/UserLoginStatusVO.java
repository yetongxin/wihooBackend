package com.yetx.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yetx.pojo.User;

public class UserLoginStatusVO {

    private String skey;

    private Boolean first;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private User user;

    public UserLoginStatusVO() {
    }

    public UserLoginStatusVO(String skey, Boolean first, User user) {
        this.skey = skey;
        this.first = first;
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserLoginStatusVO{" +
                "skey='" + skey + '\'' +
                ", first=" + first +
                ", user=" + user +
                '}';
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
