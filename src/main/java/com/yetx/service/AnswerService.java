package com.yetx.service;

public interface AnswerService {


    public void findAllAnswers(String questionId);

    public void uploadAnswer(String token,String questionId);

    public void deleteAnswer(String token,String questionId);

    public void updateAnswer(String token,String questionId);

}
