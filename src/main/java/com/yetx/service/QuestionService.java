package com.yetx.service;

public interface QuestionService {

    public void findAllQuestions();

    public void uploadNewQuestion(String token);

    public void deleteQuestion(String token,String questionId);

    public void updateQuestion(String token);

    
}
