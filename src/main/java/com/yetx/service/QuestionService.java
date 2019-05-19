package com.yetx.service;

import com.yetx.dto.QuestionDTO;
import com.yetx.pojo.Question;
import com.yetx.vo.PageVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionService {

    public PageVO findAllQuestions(Integer staPage, Integer pageSize);

    public Question uploadQuestion(String token, QuestionDTO questionDTO);

    @Transactional
    Question updateQuestion(String token, QuestionDTO questionDTO);

    public Boolean deleteQuestion(String token, String questionId);

    public List<Question> findQuestionByUserId(String userId);

    public List<Question> searchQuestionByKeyWord(String keyword);

    public Integer focusQuestion(String token,String questionId);

    public Integer disFocusQuestion(String token, String questionId);
}
