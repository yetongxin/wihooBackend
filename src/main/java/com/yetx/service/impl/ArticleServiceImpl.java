package com.yetx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yetx.dao.*;

import com.yetx.dto.CommentDTO;
import com.yetx.enums.ArticleErrorEnum;
import com.yetx.enums.CommentErrorEnum;
import com.yetx.enums.UserErrorEnum;
import com.yetx.exception.MyException;
import com.yetx.pojo.*;

import com.yetx.service.ArticleService;
import com.yetx.service.RedisService;
import com.yetx.vo.ArticleVO;
import com.yetx.vo.CommentVO;
import com.yetx.vo.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class ArticleServiceImpl implements ArticleService {
    private Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private CollectArticleMapper collectArticleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LikeArticleMapper likeArticleMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public PageVO findAllArticleByPopularity(int staPage,int pageSize) {

        PageHelper.startPage(staPage,pageSize);
        List<ArticleVO> list = articleMapper.selectByPopularity();
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
    public List<CommentVO> findAllCommentByArticleId(String articleId) {
        if(StringUtils.isEmpty(articleId))
            throw new MyException(ArticleErrorEnum.ARTICLEID_NOT_FIND);
        List<CommentVO> articleComments = commentMapper.selectCommentsvoByArticleId(articleId);
        return articleComments;
    }

    @Transactional
    @Override
    public Article saveArticle(String token, Article article) {
        String openid = redisService.findOpenidByToken(token);
        //简单判断是否合法
        if(StringUtils.isEmpty(openid))
            throw new MyException(UserErrorEnum.TOKEN_NOT_FIND);


        //不存在
        Article articleRes = new Article();
        if(article.getId()==null){
            article.setId(UUID.randomUUID().toString());
            article.setOpenid(openid);
            article.setCollectCounts(0);
            article.setLikeCounts(0);
            articleMapper.insert(article);

            articleRes = articleMapper.selectByPrimaryKey(article.getId());
        }
        //存在则更新
        else {
            Article articleDB = articleMapper.selectByPrimaryKey(article.getId());
            if(articleDB==null||!articleDB.getOpenid().equals(openid))
                throw new MyException(ArticleErrorEnum.ARTICLE_UPLOAD_WITH_ERROR_OPENID);
            articleMapper.updateByPrimaryKeySelective(article);
            articleRes = articleMapper.selectByPrimaryKey(article.getId());
        }
        return articleRes;
    }
    @Transactional
    @Override
    public Boolean deleteArticle(String token, String articleId){
        String openid = redisService.findOpenidByToken(token);
        //简单判断是否合法
        if(StringUtils.isEmpty(openid))
            throw new MyException(UserErrorEnum.TOKEN_NOT_FIND);
        if(StringUtils.isEmpty(articleId))
            throw new MyException(ArticleErrorEnum.ARTICLEID_NULL);
        articleMapper.deleteByPrimaryKey(articleId);
        return true;
    }
    @Transactional
    @Override
    public Boolean collectArticle(String token, String articleId) {
        String openid = redisService.findOpenidByToken(token);
        //简单判断是否合法
        if(StringUtils.isEmpty(openid))
            throw new MyException(UserErrorEnum.TOKEN_NOT_FIND);
        String userId = userMapper.selectByOpenid(openid).getId();
        CollectArticle collectArticle = collectArticleMapper.checkIfCollect(
                userId,articleId);
        logger.info("查询到的收藏数据：{}",collectArticle);
        if(collectArticle!=null)
           return false;
        //保存收藏记录
        CollectArticle collectArticle_db = new CollectArticle();
        collectArticle_db.setId(UUID.randomUUID().toString());
        collectArticle_db.setArticleId(articleId);
        collectArticle_db.setUserId(userId);
        collectArticleMapper.insert(collectArticle_db);
        //修改article
        Article article = articleMapper.selectByPrimaryKey(articleId);
        article.setCollectCounts(article.getCollectCounts()+1);
        articleMapper.updateByPrimaryKeySelective(article);
        return true;
    }

    @Transactional
    @Override
    public Boolean disCollectArticle(String token, String articleId) {
        String openid = redisService.findOpenidByToken(token);
        //简单判断是否合法
        if(StringUtils.isEmpty(openid))
            throw new MyException(UserErrorEnum.TOKEN_NOT_FIND);
        CollectArticle collectArticle = collectArticleMapper.checkIfCollect(
                userMapper.selectByOpenid(openid).getId(),articleId);
        if(collectArticle!=null){
            collectArticleMapper.deleteByPrimaryKey(collectArticle.getId());
            //修改article
            Article article = articleMapper.selectByPrimaryKey(articleId);
            article.setCollectCounts(article.getCollectCounts()-1);
            articleMapper.updateByPrimaryKeySelective(article);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public String commentArticle(String token, CommentDTO articleComment) {
        String openid = redisService.findOpenidByToken(token);
        //简单判断是否合法
        if(StringUtils.isEmpty(openid))
            throw new MyException(UserErrorEnum.TOKEN_NOT_FIND);
        if(articleComment==null)
            throw new MyException(CommentErrorEnum.COMMENT_NULL);
        String userId = userMapper.selectByOpenid(openid).getId();
        String articleCommentId = UUID.randomUUID().toString().replace("-","");

        Comment comment = new Comment();
        comment.setId(articleCommentId);
        comment.setLikeCounts(0);
        comment.setParentType(articleComment.getType());//设置为文章类型的评论
        comment.setContent(articleComment.getContent());
        comment.setFromUid(articleComment.getFromUid());
        comment.setToUid(articleComment.getToUid());
        comment.setParentId(articleComment.getId());
        commentMapper.insert(comment);
        return articleCommentId;
    }
    @Transactional
    @Override
    public Boolean deleteCommentArticle(String token, String articleCommentId) {
        String openid = redisService.findOpenidByToken(token);
        //简单判断是否合法
        if(StringUtils.isEmpty(openid))
            throw new MyException(UserErrorEnum.TOKEN_NOT_FIND);

        commentMapper.deleteByPrimaryKey(articleCommentId);
        commentMapper.deleteSubComment(articleCommentId);
        return true;
    }

    @Transactional
    @Override
    public Integer zanArticle(String token, String articleId){
        String openid = redisService.findOpenidByToken(token);
        //简单判断是否合法
        if(StringUtils.isEmpty(openid))
            throw new MyException(UserErrorEnum.TOKEN_NOT_FIND);
        Article article = articleMapper.selectByPrimaryKey(articleId);
        if(article==null)
            throw new MyException(ArticleErrorEnum.ARTICLEID_NOT_FIND);
        User user = userMapper.selectByOpenid(openid);
        //先查询点赞表
        LikeArticle likeArticle = likeArticleMapper.selectByUserIdAndArticleId(user.getId(),articleId);
        if(likeArticle!=null)
            return article.getLikeCounts();
        article.setLikeCounts(article.getLikeCounts()+1);
        //更新article和点赞表
        articleMapper.updateByPrimaryKey(article);
        LikeArticle likeArticleDB = new LikeArticle();
        likeArticleDB.setId(UUID.randomUUID().toString().replace("-",""));
        likeArticleDB.setArticleId(articleId);
        likeArticleDB.setUserId(user.getId());
        likeArticleMapper.insert(likeArticleDB);
        return article.getLikeCounts();
    }
    @Transactional
    @Override
    public Integer disZanArticle(String token, String articleId){
        String openid = redisService.findOpenidByToken(token);
        if(StringUtils.isEmpty(openid))
            throw new MyException(UserErrorEnum.TOKEN_NOT_FIND);
        Article article = articleMapper.selectByPrimaryKey(articleId);
        if(article==null)
            throw new MyException(ArticleErrorEnum.ARTICLEID_NOT_FIND);
        User user = userMapper.selectByOpenid(openid);
        LikeArticle likeArticle = likeArticleMapper.selectByUserIdAndArticleId(user.getId(),articleId);
        if(likeArticle==null)
            return article.getLikeCounts();
        article.setLikeCounts(article.getLikeCounts()-1);
        //更新article和点赞表
        articleMapper.updateByPrimaryKey(article);
        likeArticleMapper.deleteByPrimaryKey(likeArticle.getId());
        return article.getLikeCounts();
    }
}
