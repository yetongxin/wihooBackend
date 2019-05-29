package com.yetx.vo;

import java.util.List;

public class CollectAnswerVO {

    private String questionId;
    private String questionTitle;
    private AnswerVO answer;

    public CollectAnswerVO() {
    }

    public CollectAnswerVO(String questionId, String questionTitle, AnswerVO answer) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.answer = answer;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public AnswerVO getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerVO answer) {
        this.answer = answer;
    }
}

