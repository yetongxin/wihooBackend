package com.yetx.vo;

import com.yetx.pojo.Question;

import java.util.List;

public class QuestionDetailVO {
    private QuestionVO question;

    private List<AnswerVO> answers;

    public QuestionDetailVO(QuestionVO question, List<AnswerVO> answers) {
        this.question = question;
        this.answers = answers;
    }

    public QuestionDetailVO() {
    }

    public QuestionVO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionVO question) {
        this.question = question;
    }

    public List<AnswerVO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerVO> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "QuestionDetailVO{" +
                "question=" + question +
                ", answers=" + answers +
                '}';
    }
}
