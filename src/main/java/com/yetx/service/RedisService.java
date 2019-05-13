package com.yetx.service;

public interface RedisService {
    public String findOpenidByToken(String token);

    String findSessionKeyByToken(String token);
}
