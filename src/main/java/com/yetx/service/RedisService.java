package com.yetx.service;

import java.util.Set;

public interface RedisService {
    public String findOpenidByToken(String token);

    String findSessionKeyByToken(String token);

    void initQuestionPopuWeekJob();

    void initQuestionPopuStart();

    /**
     **新增questionpopu id:1
     */
    void zaddQuestionId(String id);

    void zdeleteQId(String id);

    Double addQuestionPopu(String id);

    boolean queryIfExist(String id);

    Double subQuestionPopu(String id);

    Set<String> getTopNQuestion();
}
