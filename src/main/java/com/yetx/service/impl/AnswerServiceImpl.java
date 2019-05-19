package com.yetx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yetx.constant.CommentType;
import com.yetx.constant.ZanType;
import com.yetx.dao.*;
import com.yetx.dto.AnswerDTO;
import com.yetx.dto.CommentDTO;
import com.yetx.enums.AuthErrorEnum;
import com.yetx.enums.CommentErrorEnum;
import com.yetx.enums.QuestionErrorEnum;
import com.yetx.exception.MyException;
import com.yetx.pojo.Answer;
import com.yetx.pojo.Comment;
import com.yetx.pojo.LikeAnswer;
import com.yetx.pojo.Question;
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
    @Autowired
    private LikeAnswerMapper likeAnswerMapper;
    private Logger logger = LoggerFactory.getLogger(AnswerServiceImpl.class);
    @Override
    public PageVO findAllAnswersOrderByZan(String questionId, Integer staPage, Integer pageSize) {
        if(staPage==null)   staPage = 1;
        if(pageSize==null)   pageSize = 5;
        if(StringUtils.isEmpty(questionId)){
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
        return pageVO;
    }

    @Override
    public PageVO findAllAnswersOrderByTime(String questionId, Integer staPage, Integer pageSize) {
        if(staPage==null)   staPage = 1;
        if(pageSize==null)   pageSize = 5;
        if(StringUtils.isEmpty(questionId)){
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

    //TODO:解决answer表的status字段,字段在mapper xml中还未修改
    @Override
    public List<CommentVO> findCommentsByAnswerId(String answerId) {
        if(StringUtils.isEmpty(answerId)){
            throw new MyException(QuestionErrorEnum.ANSWER_ID_NULL);
        }
        return commentMapper.selectCommentsVOByAnswerId(answerId);
    }

    @Transactional
    @Override
    public Answer uploadAnswer(String token, AnswerDTO answerDTO) {
        /*answerId;questionId;content;status;*/
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);
        //简单判断token是否出现过期
        if(StringUtils.isEmpty(userId)){
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        }
        if(StringUtils.isEmpty(answerDTO.getContent()))
            throw new MyException(QuestionErrorEnum.ANSWER_CONTENT_NULL);
        if(StringUtils.isEmpty(answerDTO.getQuestionId())){
           throw new MyException(QuestionErrorEnum.ANSWER_QUESID_NULL);
        }

        //构造插入数据库的answer
        //新上传，需要设置一系列的初始值
        Answer answer = new Answer();
        String answerId = IdUtils.getNewId();
        answer.setId(answerId);
        answer.setCommentCounts(0);
        answer.setLikeCounts(0);
        answer.setUserId(userId);
        answer.setStatus(answerDTO.getStatus());
        answer.setContent(answerDTO.getContent());
        answer.setQuestionId(answerDTO.getQuestionId());
        answerMapper.insert(answer);

        return answerMapper.selectByPrimaryKey(answerId);
    }



    @Transactional
    @Override
    public Answer updateAnswer(String token, AnswerDTO answerDTO) {
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);
        //简单判断token是否出现过期
        if(StringUtils.isEmpty(userId)){
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        }
        if(StringUtils.isEmpty(answerDTO.getContent()))
            throw new MyException(QuestionErrorEnum.ANSWER_CONTENT_NULL);
        Answer answer = answerMapper.selectByPrimaryKey(answerDTO.getAnswerId());
        //answerId查询不到对应的answer
        if(answer==null){
            throw new MyException(QuestionErrorEnum.ANSWER_ID_NOT_FOUND);
        }
        answer.setContent(answerDTO.getContent());
        answer.setStatus(answerDTO.getStatus());
        answer.setId(answerDTO.getAnswerId());
        answerMapper.updateByPrimaryKeySelective(answer);
        return answerMapper.selectByPrimaryKey(answerDTO.getAnswerId());

    }

    @Transactional
    @Override
    public boolean deleteAnswer(String token, String answerId) {
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);
        //简单判断token是否出现过期
        if(StringUtils.isEmpty(userId)){
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        }
        Answer answer = answerMapper.selectByPrimaryKey(answerId);
        //answerId查询不到对应的answer
        if(answer==null){
            throw new MyException(QuestionErrorEnum.ANSWER_ID_NOT_FOUND);
        }
        //userId和answerId不匹配
        if(!answer.getUserId().equals(userId)){
            throw new MyException(QuestionErrorEnum.ANSWER_ID_UID_NOTMATCH);
        }
        //删除回答的同时更新问题表的回答数
        answerMapper.deleteByPrimaryKey(answerId);
        Question question = new Question();
        question.setId(answer.getQuestionId());
        question.setAnsCounts(question.getAnsCounts()-1);
        questionMapper.updateByPrimaryKeySelective(question);
        return true;
    }

    @Transactional
    @Override
    public Integer zanAnswer(String token, String answerId) {
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);
        //简单判断token是否出现过期
        if(StringUtils.isEmpty(userId)){
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        }
        //判断是否点过赞
        if(likeAnswerMapper.countAnswerZan(userId,answerId)==0){
            answerMapper.addLikeCounts(answerId);
            LikeAnswer likeAnswer = new LikeAnswer(IdUtils.getNewId(),answerId,userId,
                    ZanType.ANS_ZAN);
            likeAnswerMapper.insert(likeAnswer);
        }
        return answerMapper.selectByPrimaryKey(answerId).getLikeCounts();
    }

    @Transactional
    @Override
    public Integer diszanAnswer(String token, String answerId) {
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);
        //简单判断token是否出现过期
        if(StringUtils.isEmpty(userId)){
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        }
        if(likeAnswerMapper.countAnswerZan(userId,answerId)!=0){
            //减answer表中的点赞数，删like_answer表中的记录
            answerMapper.subLikeCounts(answerId);
            likeAnswerMapper.deleteAnswerZan(userId,answerId);
        }
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
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        }
        commentDTO.setFromUid(userId);
        //commentDTO的id字段在对应type下的表中查询不到对应的数据
        if(commentDTO.getType()>4||commentDTO.getType()<1){
            throw new MyException(QuestionErrorEnum.COMMENT_TYPE_OOR);
        }
        //第一种评论
        else if(commentDTO.getType() == CommentType.ANSWER_COM){
            Answer answer = answerMapper.selectByPrimaryKey(commentDTO.getId());
            if(answer==null){
                throw new MyException(QuestionErrorEnum.COMMENT_FOR_NOT_FOUND);
            }
            //如果评论类型是回答，需要在answer表累加一下comment_counts
            answerMapper.addCommentCounts(answer.getId());
        }
        //第二种评论
        else if(commentDTO.getType() == CommentType.ANSWER_SUB_COM){
            Comment comment = commentMapper.selectByPrimaryKey(commentDTO.getId());
            if(comment==null){
                throw new MyException(QuestionErrorEnum.COMMENT_FOR_NOT_FOUND);
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

    //TODO:修改文章的删除评论接口
    @Override
    public Boolean deleteComment(String token, String commentId) {
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);
        //简单判断token是否出现过期
        if(StringUtils.isEmpty(userId)){
            logger.error(AuthErrorEnum.TOKEN_NOT_FIND.getMsg());
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        }
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        if(comment==null){//查不到当前commentId
            throw new MyException(CommentErrorEnum.COMMENT_NULL);
        }
        if(comment.getParentType()==CommentType.DELETE_COM){
            throw new MyException(QuestionErrorEnum.COMMENT_HAVE_DELETED);
        }
        commentMapper.deleteComment(commentId);
        return true;
    }

    //TODO:点赞回答的评论,可以考虑Redis
    @Override
    public Integer zanAnswerComment(String token, String commentId) {
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);
        //简单判断token是否出现过期
        if(StringUtils.isEmpty(userId)){
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        }
        if(likeAnswerMapper.countCommentZan(userId,commentId)==0) {
            //1.累加comment的赞数
            commentMapper.addCommentZan(commentId);
            LikeAnswer likeAnswer = new LikeAnswer(IdUtils.getNewId(),commentId,userId,ZanType.ANS_COMZAN);
            //2.插入点赞关系
            likeAnswerMapper.insert(likeAnswer);
        }
        return commentMapper.selectByPrimaryKey(commentId).getLikeCounts();
    }
    //TODO:取消点赞回答的评论
    @Override
    public Integer diszanAnswerComment(String token, String commentId) {
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);
        //简单判断token是否出现过期
        if(StringUtils.isEmpty(userId)){
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        }
        if(likeAnswerMapper.countCommentZan(userId,commentId)!=0) {
            //1.累加comment的赞数
            commentMapper.subCommentZan(commentId);
            //2.删除点赞关系
            likeAnswerMapper.deleteCommentZan(userId,commentId);
        }
        return commentMapper.selectByPrimaryKey(commentId).getLikeCounts();
    }

    boolean isAnsIdMatchUserId(String userId,String answerId){
        Answer answer = answerMapper.selectByPrimaryKey(answerId);
        //answerId查询不到对应的answer
        if(answer==null){
            throw new MyException(QuestionErrorEnum.ANSWER_ID_NOT_FOUND);
        }
        //userId和answerId不匹配
        if(!answer.getUserId().equals(userId)){
            throw new MyException(QuestionErrorEnum.ANSWER_ID_UID_NOTMATCH);
        }
        return true;
    }

}
