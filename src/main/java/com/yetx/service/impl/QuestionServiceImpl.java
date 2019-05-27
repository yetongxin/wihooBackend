package com.yetx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yetx.constant.QuestionStatus;
import com.yetx.dao.FocusQuestionMapper;
import com.yetx.dao.QuestionMapper;
import com.yetx.dao.UserMapper;
import com.yetx.dto.QuestionDTO;
import com.yetx.enums.AuthErrorEnum;
import com.yetx.enums.QuestionErrorEnum;
import com.yetx.exception.MyException;
import com.yetx.pojo.FocusQuestion;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FocusQuestionMapper focusQuestionMapper;
    private Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);


    @Override
    public PageVO findAllQuestions(Integer staPage, Integer pageSize) {
        //TODO:添加返回用户头像与昵称
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

        if(StringUtils.isEmpty(userId)){
            throw new MyException(AuthErrorEnum.USER_NOT_EXIST);
        }
        Question question = new Question();
        question.setTitle(questionDTO.getTitle());
        question.setContent(questionDTO.getContent());
        question.setUserId(userId);

        //上传新的问题
        String questionId = IdUtils.getNewId();
        question.setId(questionId);
        question.setFocusCounts(0);
        question.setAnsCounts(0);
        question.setStatus(QuestionStatus.ONSHOW);
        questionMapper.insert(question);
        //上传后即插入redis参与排序
        if(question.getStatus()==QuestionStatus.ONSHOW)
            redisService.zaddQuestionId(questionId);
        return questionMapper.selectByPrimaryKey(questionId);


    }
    @Override
    @Transactional
    public Question updateQuestion(String token, QuestionDTO questionDTO){
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);
        if(StringUtils.isEmpty(userId)){
            logger.error(AuthErrorEnum.USER_NOT_EXIST.getMsg());
            throw new MyException(AuthErrorEnum.USER_NOT_EXIST);
        }
        if(StringUtils.isEmpty(questionDTO.getQuestionId())){
            logger.error(QuestionErrorEnum.QUESTION_ID_NULL.getMsg());
            throw new MyException(QuestionErrorEnum.QUESTION_ID_NULL);
        }
        Question findQ = questionMapper.selectByPrimaryKey(questionDTO.getQuestionId());
        if(findQ==null)
            throw new MyException(QuestionErrorEnum.QUESTION_NOT_FOUND);
        if(!findQ.getUserId().equals(userId))
            throw new MyException(QuestionErrorEnum.QUESTION_ID_UID_NOTMATCH);
        Question question = new Question();
        question.setId(findQ.getId());
        question.setTitle(questionDTO.getTitle());
        question.setContent(questionDTO.getContent());
        questionMapper.updateByPrimaryKeySelective(question);
        //确认是上传后即插入redis参与排序
        if(findQ.getStatus()==QuestionStatus.ONSHOW)
            redisService.addQuestionPopu(findQ.getId());
        else if(question.getStatus()==QuestionStatus.ONSHOW)
            redisService.zaddQuestionId(findQ.getId());
        return questionMapper.selectByPrimaryKey(questionDTO.getQuestionId());
}

    @Transactional
    @Override
    public Boolean deleteQuestion(String token, String questionId) {
        //此处的删除只是改变Question的状态，因为用户删除问题后不应该删除掉对应的回答（有人想保留回答）
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);

        if(StringUtils.isEmpty(questionId)){
            throw new MyException(QuestionErrorEnum.QUESTION_ID_NULL);
        }
        else {
            Question findRes = questionMapper.selectByPrimaryKey(questionId);
            //防止出现用户id与问题id不同
            if(!findRes.getUserId().equals(userId)) {
                throw new MyException(QuestionErrorEnum.QUESTION_ID_UID_NOTMATCH);
            }
            else if(findRes.getStatus()==QuestionStatus.HAVA_DELETE){
                throw new MyException(QuestionErrorEnum.QUESTION_HAVA_DELETE);
            }
            else{
                Question question = new Question();
                question.setId(questionId);
                question.setStatus(QuestionStatus.HAVA_DELETE);
                question.setTitle("该问题已删除");
                question.setContent("该问题已删除");
                questionMapper.updateByPrimaryKeySelective(question);
            }
        }
        redisService.zdeleteQId(questionId);
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
    @Transactional
    public Integer focusQuestion(String token, String questionId) {
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);

        if(StringUtils.isEmpty(userId)){
            throw new MyException(AuthErrorEnum.USER_NOT_EXIST);
        }
        if(focusQuestionMapper.countUserFocusQues(userId,questionId)==0){
            questionMapper.addFocusCounts(questionId);
            focusQuestionMapper.insert(new FocusQuestion(IdUtils.getNewId(),userId,questionId));
        }
        //添加热度
        redisService.addQuestionPopu(questionId);
        return questionMapper.selectByPrimaryKey(questionId).getFocusCounts();
    }

    @Transactional
    @Override
    public Integer disFocusQuestion(String token, String questionId){
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);
        if(StringUtils.isEmpty(userId)){
            throw new MyException(AuthErrorEnum.USER_NOT_EXIST);
        }
        if (focusQuestionMapper.countUserFocusQues(userId,questionId)!=0){
            questionMapper.subFocusCounts(questionId);
            focusQuestionMapper.deleteUserFocusQues(userId,questionId);
        }
        //减少热度
        redisService.subQuestionPopu(questionId);
        return questionMapper.selectByPrimaryKey(questionId).getFocusCounts();
    }

    @Override
    public List<Question> findAllfocusQuestion(String token){
        String openid = redisService.findOpenidByToken(token);
        if(StringUtils.isEmpty(openid)){
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        }
        String userId = userMapper.selectUserIdByOpenId(openid);
        return questionMapper.selectFocusQuestion(userId);
    }

    @Override
    public List<Question> findTopNQuestion() {
        Set<String> set = redisService.getTopNQuestion();
        if(set==null){
            return (List<Question>) findAllQuestions(1,20).getCurData();
        }
        else {
            List<Question> questions = new ArrayList<>();
            for(String str:set){
                questions.add(questionMapper.selectByPrimaryKey(str));
            }
            return  questions;
        }

    }
}
