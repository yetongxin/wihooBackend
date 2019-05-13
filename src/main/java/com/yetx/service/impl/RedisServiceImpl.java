package com.yetx.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yetx.enums.AuthErrorEnum;
import com.yetx.exception.MyException;
import com.yetx.service.RedisService;
import com.yetx.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisOperator redisOperator;
    @Override
    public String findOpenidByToken(String token) {
        String res = redisOperator.get("TOKEN:"+token);
        if(StringUtils.isEmpty(res))
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        JSONObject jsonObject = (JSONObject) JSONObject.parse(res);
        return jsonObject.get("openid")+"";
    }
    @Override
    public String findSessionKeyByToken(String token) {
        String res = redisOperator.get("TOKEN:"+token);
        if(StringUtils.isEmpty(res))
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        JSONObject jsonObject = (JSONObject) JSONObject.parse(res);
        return jsonObject.get("sessionKey")+"";
    }
}
