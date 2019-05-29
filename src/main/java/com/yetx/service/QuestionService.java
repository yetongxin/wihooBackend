package com.yetx.service;

import com.yetx.dto.QuestionDTO;
import com.yetx.pojo.Question;
import com.yetx.vo.PageVO;
import com.yetx.vo.QuestionDetailVO;
import com.yetx.vo.QuestionVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionService {

    public PageVO findAllQuestionsPopu(Integer staPage,Integer pageSize);
    //这个是按时间查找的
    public PageVO findAllQuestions(Integer staPage, Integer pageSize);

    public Question uploadQuestion(String token, QuestionDTO questionDTO);

    Question updateQuestion(String token, QuestionDTO questionDTO);

    public Boolean deleteQuestion(String token, String questionId);

    public List<QuestionVO> findQuestionByUserId(String userId);

    public List<QuestionVO> searchQuestionByKeyWord(String keyword);

    public Integer focusQuestion(String token,String questionId);

    public Integer disFocusQuestion(String token, String questionId);

    List<QuestionVO> findAllfocusQuestion(String token);

    //这个靠redis查找的
    List<QuestionVO> findTopNQuestion();

    QuestionDetailVO getQuestionDetail(String questionId);
}
