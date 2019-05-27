package com.yetx.service;

import com.yetx.dto.QuestionDTO;
import com.yetx.pojo.Question;
import com.yetx.vo.PageVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionService {

    //这个是按时间查找的
    public PageVO findAllQuestions(Integer staPage, Integer pageSize);

    public Question uploadQuestion(String token, QuestionDTO questionDTO);

    Question updateQuestion(String token, QuestionDTO questionDTO);

    public Boolean deleteQuestion(String token, String questionId);

    public List<Question> findQuestionByUserId(String userId);

    public List<Question> searchQuestionByKeyWord(String keyword);

    public Integer focusQuestion(String token,String questionId);

    public Integer disFocusQuestion(String token, String questionId);

    List<Question> findAllfocusQuestion(String token);

    //这个靠redis查找的
    List<Question> findTopNQuestion();
}
