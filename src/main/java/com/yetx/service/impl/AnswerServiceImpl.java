package com.yetx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yetx.constant.CommentType;
import com.yetx.dao.AnswerMapper;
import com.yetx.dao.CommentMapper;
import com.yetx.dao.QuestionMapper;
import com.yetx.dao.UserMapper;
import com.yetx.dto.AnswerDTO;
import com.yetx.dto.CommentDTO;
import com.yetx.enums.AuthErrorEnum;
import com.yetx.enums.QuestionErrorEnum;
import com.yetx.exception.MyException;
import com.yetx.pojo.Answer;
import com.yetx.pojo.Comment;
import com.yetx.service.AnswerService;
import com.yetx.service.RedisService;
import com.yetx.utils.IdUtils;
import com.yetx.vo.CommentVO;
import com.yetx.vo.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CommentMapper commentMapper;

    private Logger logger = LoggerFactory.getLogger(AnswerServiceImpl.class);
    @Override
    public PageVO findAllAnswersOrderByZan(String questionId, Integer staPage, Integer pageSize) {
        if(staPage==null)   staPage = 1;
        if(pageSize==null)   pageSize = 5;
        if(StringUtils.isEmpty(questionId)){
            logger.error(QuestionErrorEnum.QUESTION_ID_NULL.getMsg());
            throw new MyException(QuestionErrorEnum.QUESTION_ID_NULL);
        }
        PageHelper.startPage(staPage,pageSize);
        List<Answer> list = answerMapper.selectByQuestionIdOrderByZan(questionId);
        PageInfo pageInfo = new PageInfo(list);

        //返回前端需要的PageVO
        PageVO pageVO = new PageVO();
        pageVO.setCurData(pageInfo.getList());
        pageVO.setPageNum(pageInfo.getPages());
        pageVO.setCurPage(staPage);
        pageVO.setRecords(pageInfo.getTotal());
        return pageVO;    }

    @Override
    public PageVO findAllAnswersOrderByTime(String questionId, Integer staPage, Integer pageSize) {
        if(staPage==null)   staPage = 1;
        if(pageSize==null)   pageSize = 5;
        if(StringUtils.isEmpty(questionId)){
            logger.error(QuestionErrorEnum.QUESTION_ID_NULL.getMsg());
            throw new MyException(QuestionErrorEnum.QUESTION_ID_NULL);
        }
        else {
            PageHelper.startPage(staPage,pageSize);
            List<Answer> list = answerMapper.selectByQuestionIdOrderByTime(questionId);
            PageInfo pageInfo = new PageInfo(list);

            //返回前端需要的PageVO
            PageVO pageVO = new PageVO();
            pageVO.setCurData(pageInfo.getList());
            pageVO.setPageNum(pageInfo.getPages());
            pageVO.setCurPage(staPage);
            pageVO.setRecords(pageInfo.getTotal());
            return pageVO;
        }
    }

    @Override
    public List<CommentVO> findCommentsByAnswerId(String answerId) {
        if(StringUtils.isEmpty(answerId)){
            logger.error(QuestionErrorEnum.ANSWER_ID_NULL.getMsg());
            throw new MyException(QuestionErrorEnum.ANSWER_ID_NULL);
        }
        return commentMapper.selectCommentsVOByAnswerId(answerId);
    }

    @Transactional
    @Override
    public Answer uploadAnswer(String token, AnswerDTO answerDTO) {
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);
        //简单判断token是否出现过期
        if(StringUtils.isEmpty(userId)){
            logger.error(AuthErrorEnum.TOKEN_NOT_FIND.getMsg());
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        }
        else {
            //构造插入数据库的answer

            //新上传，需要设置一系列的初始值
            if(StringUtils.isEmpty(answerDTO.getAnswerId())){
                Answer answer = new Answer();
                String answerId = IdUtils.getNewId();
                answer.setId(answerId);
                answer.setCommentCounts(0);
                answer.setLikeCounts(0);
                answer.setUserId(answerDTO.getUserId());
                answer.setStatus(answerDTO.getStatus());
                answer.setContent(answerDTO.getContent());
                answer.setStatus(answerDTO.getStatus());
                answerMapper.insert(answer);
                return answerMapper.selectByPrimaryKey(answerId);
            }
            else{//更新状态或者内容
                Answer answer = answerMapper.selectByPrimaryKey(answerDTO.getAnswerId());
                //answerId查询不到对应的answer
                if(answer==null){
                    logger.error(QuestionErrorEnum.ANSWER_ID_NOT_FOUND.getMsg());
                    throw new MyException(QuestionErrorEnum.ANSWER_ID_NOT_FOUND);
                }
                //userId和answerId不匹配
                if(!answer.getUserId().equals(userId)){
                    logger.error(QuestionErrorEnum.ANSWER_ID_UID_NOTMATCH.getMsg());
                    throw new MyException(QuestionErrorEnum.ANSWER_ID_UID_NOTMATCH);
                }
                answer.setContent(answerDTO.getContent());
                answer.setStatus(answerDTO.getStatus());
                answer.setId(answerDTO.getAnswerId());
                answerMapper.updateByPrimaryKeySelective(answer);
                return answerMapper.selectByPrimaryKey(answerDTO.getAnswerId());
            }
        }
    }

    @Transactional
    @Override
    public boolean deleteAnswer(String token, String answerId) {
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);
        //简单判断token是否出现过期
        if(StringUtils.isEmpty(userId)){
            logger.error(AuthErrorEnum.TOKEN_NOT_FIND.getMsg());
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        }

        if(isAnsIdMatchUserId(userId,answerId)){
            answerMapper.deleteByPrimaryKey(answerId);
        }
        return true;
    }

    @Override
    public Integer zanAnswer(String token, String answerId) {
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);
        //简单判断token是否出现过期
        if(StringUtils.isEmpty(userId)){
            logger.error(AuthErrorEnum.TOKEN_NOT_FIND.getMsg());
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        }
        answerMapper.addLikeCounts(answerId);
        return answerMapper.selectByPrimaryKey(answerId).getLikeCounts();
    }

    @Override
    public Integer diszanAnswer(String token, String answerId) {
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);
        //简单判断token是否出现过期
        if(StringUtils.isEmpty(userId)){
            logger.error(AuthErrorEnum.TOKEN_NOT_FIND.getMsg());
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        }
        answerMapper.subLikeCounts(answerId);
        return answerMapper.selectByPrimaryKey(answerId).getLikeCounts();
    }

    //存在两种评论：1.回答的直接评论   2.作为回答评论的子评论
    @Transactional
    @Override
    public String commentAnswer(String token, CommentDTO commentDTO) {
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);
        //简单判断token是否出现过期
        if(StringUtils.isEmpty(userId)){
            logger.error(AuthErrorEnum.TOKEN_NOT_FIND.getMsg());
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        }
        //userId与传入的fromUid不匹配
        if(!userId.equals(commentDTO.getFromUid())){
            logger.error(QuestionErrorEnum.COMMENT_FROMUID_NOWUID_NOTMATCH.getMsg());
            throw new MyException(QuestionErrorEnum.COMMENT_FROMUID_NOWUID_NOTMATCH);
        }
        //commentDTO的id字段在对应type下的表中查询不到对应的数据
        if(commentDTO.getType()>4||commentDTO.getType()<1){
            logger.error(QuestionErrorEnum.COMMENT_TYPE_OOR.getMsg());
            throw new MyException(QuestionErrorEnum.COMMENT_TYPE_OOR);

        }
        //第一种评论
        else if(commentDTO.getType() == CommentType.ANSWER_COM){
            Answer answer = answerMapper.selectByPrimaryKey(commentDTO.getId());
            if(answer==null){
                logger.error(QuestionErrorEnum.COMMENT_TYPE_ID_NOTMATCH.getMsg());
                throw new MyException(QuestionErrorEnum.COMMENT_FROMUID_NOWUID_NOTMATCH);
            }
            //如果评论类型是回答，需要在answer表累加一下comment_counts
            answerMapper.addCommentCounts(answer.getId());
        }
        //第二种评论
        else if(commentDTO.getType() == CommentType.ANSWER_SUB_COM){
            Comment comment = commentMapper.selectByPrimaryKey(commentDTO.getId());
            if(comment==null){
                logger.error(QuestionErrorEnum.COMMENT_TYPE_ID_NOTMATCH.getMsg());
                throw new MyException(QuestionErrorEnum.COMMENT_FROMUID_NOWUID_NOTMATCH);
            }
        }
        //通过各项检测,可以插入到数据库中
        Comment comment = new Comment();
        comment.setId(IdUtils.getNewId());
        comment.setParentType(commentDTO.getType());
        comment.setParentId(commentDTO.getId());
        comment.setFromUid(commentDTO.getFromUid());
        comment.setToUid(commentDTO.getToUid());
        comment.setContent(commentDTO.getContent());
        comment.setLikeCounts(0);
        commentMapper.insert(comment);
        return comment.getId();
    }

    //TODO:删除评论
    @Override
    public Boolean deleteComment(String token, String commentId) {
        return null;
    }

    //TODO:点赞回答的评论
    @Override
    public Integer zanAnswerComment(String token, String answerId) {
        return null;
    }
    //TODO:取消点赞回答的评论
    @Override
    public Integer diszanAnswerComment(String token, String answerId) {
        return null;
    }

    boolean isAnsIdMatchUserId(String userId,String answerId){
        Answer answer = answerMapper.selectByPrimaryKey(answerId);
        //answerId查询不到对应的answer
        if(answer==null){
            logger.error(QuestionErrorEnum.ANSWER_ID_NOT_FOUND.getMsg());
            throw new MyException(QuestionErrorEnum.ANSWER_ID_NOT_FOUND);
        }
        //userId和answerId不匹配
        if(!answer.getUserId().equals(userId)){
            logger.error(QuestionErrorEnum.ANSWER_ID_UID_NOTMATCH.getMsg());
            throw new MyException(QuestionErrorEnum.ANSWER_ID_UID_NOTMATCH);
        }
        return true;
    }

}
