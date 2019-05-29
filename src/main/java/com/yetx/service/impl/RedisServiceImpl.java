package com.yetx.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yetx.enums.AuthErrorEnum;
import com.yetx.exception.MyException;
import com.yetx.pojo.Question;
import com.yetx.service.QuestionService;
import com.yetx.service.RedisService;
import com.yetx.utils.RedisOperator;
import com.yetx.vo.QuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

@Service
public class RedisServiceImpl implements RedisService {

    //TODO: 后期修改为20
    private final int N = 10;
    private final String QUESTION_KEY = "questionPopu";
    private final String QTIME_PRE = "qtime:";
    private final int POSI_WEIGHT = 10;
    private final int NEG_WEIGHT = -2;
    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private QuestionService questionService;
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

    @Override
    public void initQuestionPopuWeekJob(){
        Long now = System.currentTimeMillis();
        Set<String> value = redisOperator.zGetAll(QUESTION_KEY);
        if(value.size()>0) {
            for (String val : value) {
                if (redisOperator.zSize(QUESTION_KEY)<= N)//少于N个不删
                    break;
                //System.out.println(QTIME_PRE+val);
                Long time = redisOperator.getTime(QTIME_PRE+val);
                if ((now - time) >= 86400000) {//超过一天没点击，删除掉86400000
                    redisOperator.zRemove(QUESTION_KEY,val);
                    redisOperator.removeTime(QTIME_PRE+val);
                }
            }
        }
        else {
            initQuestionPopuStart();
        }
    }
    @Override
    public void initQuestionPopuStart(){
        redisOperator.del(QUESTION_KEY);
        redisOperator.deleteTimeNameSpace(QTIME_PRE);
        //TODO: 修改为按照点赞数排行的
        List<QuestionVO> list = (List<QuestionVO>) questionService.findAllQuestionsPopu(1, N).getCurData();
        for(QuestionVO question:list){
            //初始化，question排行榜初始为按回答数排序的几个。
            redisOperator.zAdd(QUESTION_KEY,question.getId(),200);
            redisOperator.setTime(QTIME_PRE+question.getId(),System.currentTimeMillis());
        }
    }

    //新增并设置score为1
    @Override
    public void zaddQuestionId(String id){
        redisOperator.zAdd(QUESTION_KEY,id,1);
        redisOperator.setTime(QTIME_PRE+id,System.currentTimeMillis());
    }

    @Override
    public void zdeleteQId(String id) {
        redisOperator.zRemove(QUESTION_KEY,id);
        redisOperator.removeTime(QTIME_PRE+id);
    }
    //增加score
    @Override
    public Double addQuestionPopu(String id){
        redisOperator.setTime(QTIME_PRE+id,System.currentTimeMillis());
        return redisOperator.zIncrement(QUESTION_KEY,id,POSI_WEIGHT);
    }
    @Override
    public boolean queryIfExist(String id){
        return redisOperator.queryIfExist(QUESTION_KEY,id);
    }
    @Override
    public Double subQuestionPopu(String id){

        redisOperator.setTime(QTIME_PRE+id,System.currentTimeMillis());
        //return redisOperator.zRemove(QUESTION_KEY,id);
        return redisOperator.zIncrement(QUESTION_KEY,id,NEG_WEIGHT);
    }
    @Override
    public Set<String> getTopNQuestion(){

        return redisOperator.zGetTopN(QUESTION_KEY,0,N-1);
    }
}
