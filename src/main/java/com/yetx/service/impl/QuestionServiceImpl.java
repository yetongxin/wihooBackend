package com.yetx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yetx.dao.QuestionMapper;
import com.yetx.dao.UserMapper;
import com.yetx.dto.QuestionDTO;
import com.yetx.enums.QuestionErrorEnum;
import com.yetx.exception.MyException;
import com.yetx.pojo.Question;
import com.yetx.service.QuestionService;
import com.yetx.service.RedisService;
import com.yetx.utils.IdUtils;
import com.yetx.vo.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserMapper userMapper;
    private Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);
    @Override
    public PageVO findAllQuestions(Integer staPage, Integer pageSize) {
        if(staPage==null)   staPage = 1;
        if(pageSize==null)  pageSize = 5;
        PageHelper.startPage(staPage,pageSize);
        List<Question> list = questionMapper.selectOrderByTime();
        PageInfo pageInfo = new PageInfo(list);

        //返回前端需要的PageVO
        PageVO pageVO = new PageVO();
        pageVO.setCurData(pageInfo.getList());
        pageVO.setPageNum(pageInfo.getPages());
        pageVO.setCurPage(staPage);
        pageVO.setRecords(pageInfo.getTotal());

        return pageVO;
    }

    @Override
    @Transactional
    public Question uploadQuestion(String token, QuestionDTO questionDTO) {
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);

        Question question = new Question();
        question.setTitle(questionDTO.getTitle());
        question.setContent(questionDTO.getContent());
        question.setUserId(userId);

        //上传新的问题
        if(StringUtils.isEmpty(questionDTO.getQuestionId())){
            String questionId = IdUtils.getNewId();
            question.setId(questionId);
            question.setFocusCounts(0);
            question.setAnsCounts(0);
            questionMapper.insert(question);
            return questionMapper.selectByPrimaryKey(questionId);
        }
        else{//更新
            question.setId(questionDTO.getQuestionId());
            questionMapper.updateByPrimaryKeySelective(question);
            return questionMapper.selectByPrimaryKey(questionDTO.getQuestionId());
        }
    }

    @Transactional
    @Override
    public Boolean deleteQuestion(String token, String questionId) {
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);

        if(StringUtils.isEmpty(questionId)){
            logger.error(QuestionErrorEnum.QUESTION_ID_NULL.getMsg());
            throw new MyException(QuestionErrorEnum.QUESTION_ID_NULL);
        }
        else {
            //防止出现用户id与问题id不同
            if(questionMapper.selectByPrimaryKey(questionId).getUserId().equals(userId)) {
                questionMapper.deleteByPrimaryKey(questionId);
                //TODO:还需要删除对应的回答
            }
            else{
                logger.error(QuestionErrorEnum.QUESTION_ID_UID_NOTMATCH.getMsg());
                throw new MyException(QuestionErrorEnum.QUESTION_ID_UID_NOTMATCH);
            }


        }
        return true;
    }

    @Override
    public List<Question> findQuestionByUserId(String userId) {
        if(StringUtils.isEmpty(userId)){
            throw new MyException(QuestionErrorEnum.QUESTION_UID_NULL);
        }
        return questionMapper.selectByUserId(userId);
    }

    @Override
    public List<Question> searchQuestionByKeyWord(String keyword) {
        if(StringUtils.isEmpty(keyword)) {
            logger.error(QuestionErrorEnum.QUESTION_KEYWORDS_NULL.getMsg());
            throw new MyException(QuestionErrorEnum.QUESTION_KEYWORDS_NULL);
        }
        List<String> keywords = Arrays.asList(keyword.split("\\s+"));
        List<Question> list = questionMapper.selectByKeyWords(keywords);
        return list;
    }

    //TODO:关注问题
    @Override
    public void focusQuestion(String token, String questionId) {

    }
}
