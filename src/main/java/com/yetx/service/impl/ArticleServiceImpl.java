package com.yetx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yetx.constant.CommentType;
import com.yetx.dao.*;

import com.yetx.dto.ArticleDTO;
import com.yetx.dto.CommentDTO;
import com.yetx.enums.*;
import com.yetx.exception.MyException;
import com.yetx.pojo.*;

import com.yetx.service.ArticleService;
import com.yetx.service.RedisService;
import com.yetx.utils.IdUtils;
import com.yetx.vo.*;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private UserFanMapper userFanMapper;
    @Override
    public DraftVO findUserDraft(String token) {
        String openid = redisService.findOpenidByToken(token);
        if(StringUtils.isEmpty(openid))
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        String userId = userMapper.selectUserIdByOpenId(openid);
        return articleMapper.selectDraftByUserId(userId);

    }

    @Override
    public ArticleDetailVO findArticleDetailByAId(String token,String articleId) {
        Boolean hasZan = false,hasCollect = false,hasFollow = false;
        System.out.println("token:"+token);
        if(StringUtils.isEmpty(articleId))
            throw new MyException(ArticleErrorEnum.ARTICLEID_NULL);
        ArticleVO articleVO = articleMapper.selectVOByArticleId(articleId);
        ArticleDetailVO articleDetailVO = new ArticleDetailVO(articleVO);
        List<CommentVO> commentVOList = commentMapper.selectCommentsvoByArticleId(articleId);
        //如果不是已登录用户
        if(!token.equals("noToken")){
            String openid = redisService.findOpenidByToken(token);
            if(StringUtils.isEmpty(openid))
                throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
            String userId = userMapper.selectUserIdByOpenId(openid);
            hasZan = likeArticleMapper.countArticleZan(userId, articleId) != 0;
            //hasCollect = collectArticleMapper.countArticleCollect(userId,articleId)!=0;//TODO 未来可以加入hasCollect
            hasCollect = false;
            hasFollow = userFanMapper.countIfFollow(userId,articleVO.getUserId())!=0;
            //设置每个评论是否点过赞
            for(CommentVO commentVO:commentVOList){
                commentVO.setHasZan(likeArticleMapper.countCommentZan(userId, commentVO.getId()) != 0);
                for(SubCommentVO subCommentVO:commentVO.getSubComments()){
                    subCommentVO.setHasZan(likeArticleMapper.countCommentZan(userId,subCommentVO.getId())!=0);
                }
            }
        }

        articleDetailVO.setComments(commentVOList);
        articleDetailVO.setHasCollect(hasCollect);
        articleDetailVO.setHasZan(hasZan);
        articleDetailVO.setHasFollow(hasFollow);

        return articleDetailVO;
    }

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
    public PageVO findAllArticleByTime(int staPage, int pageSize) {

        PageHelper.startPage(staPage,pageSize);
        List<ArticleVO> list = articleMapper.selectByTime();
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
        String userId = userMapper.selectUserIdByOpenId(openid);
        //简单判断是否合法
        if(StringUtils.isEmpty(openid)||StringUtils.isEmpty(userId))
            throw new MyException(UserErrorEnum.TOKEN_NOT_FIND);


        //不存在
        Article articleRes = new Article();
        if(article.getId()==null){
            article.setId(UUID.randomUUID().toString());
            article.setUserId(userId);
            article.setCollectCounts(0);
            article.setLikeCounts(0);
            articleMapper.insert(article);

            articleRes = articleMapper.selectByPrimaryKey(article.getId());
        }
        //存在则更新
        else {
            Article articleDB = articleMapper.selectByPrimaryKey(article.getId());
            if(articleDB==null||!articleDB.getUserId().equals(userId))
                throw new MyException(ArticleErrorEnum.ARTICLE_UPLOAD_WITH_ERROR_USERID);
            articleMapper.updateByPrimaryKeySelective(article);
            articleRes = articleMapper.selectByPrimaryKey(article.getId());
        }
        return articleRes;
    }
    @Transactional
    @Override
    public String uploadArticle(String token, ArticleDTO articleDTO) {
        String openid = redisService.findOpenidByToken(token);
        if(StringUtils.isEmpty(openid))
            throw new MyException(UserErrorEnum.TOKEN_NOT_FIND);
        String userId = userMapper.selectUserIdByOpenId(openid);
        //简单判断是否合法
        if(StringUtils.isEmpty(userId))
            throw new MyException(UserErrorEnum.TOKEN_NOT_FIND);
        if(StringUtils.isEmpty(articleDTO.getContent())||StringUtils.isEmpty(articleDTO.getTitle())||
                StringUtils.isEmpty(articleDTO.getCover())|| articleDTO.getStatus()==null)
            throw new MyException(ArticleErrorEnum.UPLOAD_PARAMS_ERROR);

        //不存在
        Article article = new Article();
        String articleId = IdUtils.getNewId();
        article.setId(articleId);article.setUserId(userId);
        article.setLikeCounts(0);article.setCollectCounts(0);
        article.setTitle(articleDTO.getTitle());
        article.setCover(articleDTO.getCover());
        article.setContent(articleDTO.getContent());
        article.setStatus(articleDTO.getStatus());
        //TODO 未来文章可添加tags:
//        if(articleDTO.getTags()!=null) {
//            for (String str : articleDTO.getTags()) {
//                Tag tag = new Tag(IdUtils.getNewId(), articleId, str);
//                tagMapper.insert(tag);
//            }
//        }
        articleMapper.insert(article);
        Article articleRes = articleMapper.selectByPrimaryKey(articleId);

        //TODO 1.这里改成true false OK了 2.这里改成返回Id
        if(articleRes!=null)    return articleRes.getId();
        else return "";


    }

    //TODO: update标签
    @Override
    public String  updateArticle(String token, ArticleDTO articleDTO){
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);
        if(StringUtils.isEmpty(userId)){
            throw new MyException(UserErrorEnum.TOKEN_NOT_FIND);
        }
        if(StringUtils.isEmpty(articleDTO.getId())){
            throw new MyException(ArticleErrorEnum.UPLOAD_ARTICLEID_NULL);
        }
        Article articleDB = articleMapper.selectByPrimaryKey(articleDTO.getId());
        if(articleDB==null){
            throw new MyException(ArticleErrorEnum.ARTICLEID_NOT_FIND);
        }
        Article article = new Article();
        article.setId(articleDTO.getId());
        article.setStatus(articleDTO.getStatus());
        article.setContent(articleDTO.getContent());
        article.setTitle(articleDTO.getTitle());
        article.setCover(articleDTO.getCover());

        //TODO: 未来可添加TAGS
//        List<String> tags = articleDTO.getTags();
//        if(articleDTO.getTags()!=null){
//            tagMapper.deleteTagByArticleId(articleDTO.getId());
//            for(String str:tags){
//                Tag tag = new Tag(IdUtils.getNewId(),articleDTO.getId(),str);
//                tagMapper.insert(tag);
//            }
//        }

        //TODO:
        articleMapper.updateByPrimaryKeySelective(article);
//        if(articleDB.getOpenid())
        return articleDTO.getId();

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
        tagMapper.deleteTagByArticleId(articleId);
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

    //TODO:修改该commentArticle
    @Transactional
    @Override
    public String commentArticle(String token, CommentDTO articleComment) {
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);
        String parentId = "";
        if(StringUtils.isEmpty(userId))
            throw new MyException(UserErrorEnum.TOKEN_NOT_FIND);
        //简单判断是否合法
        if(StringUtils.isEmpty(openid))
            throw new MyException(UserErrorEnum.TOKEN_NOT_FIND);
        if(articleComment==null)
            throw new MyException(CommentErrorEnum.COMMENT_NULL);

        if(StringUtils.isEmpty(articleComment.getContent()))
            throw new MyException(QuestionErrorEnum.COMMENT_CONTENT_NULL);
        String articleCommentId = UUID.randomUUID().toString().replace("-","");
        String toUid = "";
        if(articleComment.getType()== CommentType.ARTICLE_COM){//直接评论文章
            //找出该文章
            Article article = articleMapper.selectByPrimaryKey(articleComment.getId());
            if(article==null)
                throw new MyException(QuestionErrorEnum.COMMENT_TYPE_ID_NOTMATCH);
            //设置被评论者id,因为这里前端不传，查article
            toUid = article.getUserId();
            parentId = article.getId();
        }
        else if(articleComment.getType()==CommentType.ARTICLE_SUB_COM){//评论 评论
            //找出这条评论
            Comment comment2 = commentMapper.selectByPrimaryKey(articleComment.getId());
            if(comment2==null)
                throw new MyException(QuestionErrorEnum.COMMENT_TYPE_ID_NOTMATCH);
            if(StringUtils.isEmpty(articleComment.getToUid()))//TODO:最好验证这个toUid是否真实存在
                throw new MyException(QuestionErrorEnum.COMMENT_TOUID_NOT_FOUNT);

            if((comment2=commentMapper.selectByPrimaryKey(comment2.getParentId()))!=null){
                //说明他是评论二级评论，parent_id对应的是commentid的id
                parentId = comment2.getId();
            }else {
                //说明他是评论一级评论，parent_id对应的是answerid,最好articleMapper查以下
                parentId = comment2.getParentId();
            }

            //设置被评论者id
            toUid = articleComment.getToUid();
        }
        Comment comment = new Comment();
        comment.setId(articleCommentId);
        comment.setLikeCounts(0);
        comment.setParentType(articleComment.getType());//设置为文章类型的评论
        comment.setContent(articleComment.getContent());
        comment.setFromUid(userId);
        comment.setToUid(toUid);
        comment.setParentId(parentId);
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
        Comment comment = commentMapper.selectByPrimaryKey(articleCommentId);
        if(comment==null){
            throw new MyException(CommentErrorEnum.COMMENT_NULL);
        }
        comment.setContent("该评论已删除");
        commentMapper.updateByPrimaryKey(comment);
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
        String userId = userMapper.selectUserIdByOpenId(openid);
        //先查询点赞表
        LikeArticle likeArticle = likeArticleMapper.selectByUserIdAndArticleId(userId,articleId);
        if(likeArticle!=null)
            return article.getLikeCounts();
        article.setLikeCounts(article.getLikeCounts()+1);//给article表中的likeCounts字段累加1
        //更新article和点赞表
        articleMapper.updateByPrimaryKey(article);
        LikeArticle likeArticleDB = new LikeArticle();
        likeArticleDB.setId(UUID.randomUUID().toString().replace("-",""));
        likeArticleDB.setLikeTypeId(articleId);
        likeArticleDB.setType(1);//设置为1
        likeArticleDB.setUserId(userId);
        likeArticleMapper.insert(likeArticleDB);
        return article.getLikeCounts();
    }
    @Transactional
    @Override
    public Integer zanArticleComment(String token, String commentId){
        String openid = redisService.findOpenidByToken(token);
        //简单判断是否合法
        if(StringUtils.isEmpty(openid))
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        String userId = userMapper.selectUserIdByOpenId(openid);
        //先查出对应的评论存不存在
        if(StringUtils.isEmpty(commentId))
            throw new MyException(CommentErrorEnum.COMMENT_ID_NULL);
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        if(comment==null)
            throw new MyException(CommentErrorEnum.COMMENT_NULL);
        boolean hasZan = likeArticleMapper.countCommentZan(userId, commentId) != 0;

        if(hasZan)
            return comment.getLikeCounts();
        else{
            LikeArticle likeArticle = new LikeArticle();
            likeArticle.setId(IdUtils.getNewId());
            likeArticle.setLikeTypeId(commentId);
            likeArticle.setUserId(userId);
            likeArticle.setType(2);//设置为2
            likeArticleMapper.insert(likeArticle);
            comment.setLikeCounts(comment.getLikeCounts()+1);
            commentMapper.updateByPrimaryKey(comment);
            return comment.getLikeCounts();
        }
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
        String userId = userMapper.selectUserIdByOpenId(openid);
        LikeArticle likeArticle = likeArticleMapper.selectByUserIdAndArticleId(userId,articleId);
        if(likeArticle==null)
            return article.getLikeCounts();
        article.setLikeCounts(article.getLikeCounts()-1);
        //更新article和点赞表
        articleMapper.updateByPrimaryKey(article);
//        likeArticleMapper.deleteArticleZan(userId,articleId);
        likeArticleMapper.deleteByPrimaryKey(likeArticle.getId());
        return article.getLikeCounts();
    }

    @Override
    @Transactional
    public Integer disZanArticleComment(String token, String commentId){
        String openid = redisService.findOpenidByToken(token);
        if(StringUtils.isEmpty(openid))
            throw new MyException(UserErrorEnum.TOKEN_NOT_FIND);
        if(StringUtils.isEmpty(commentId))
            throw new MyException(CommentErrorEnum.COMMENT_ID_NULL);
        Comment comment= commentMapper.selectByPrimaryKey(commentId);
        if(comment==null)
            throw new MyException(CommentErrorEnum.COMMENT_NULL);
        String userId = userMapper.selectUserIdByOpenId(openid);

        boolean hasZan = likeArticleMapper.countCommentZan(userId, commentId) != 0;
        if(hasZan){
            likeArticleMapper.deleteCommentZan(userId,commentId);
            comment.setLikeCounts(comment.getLikeCounts()-1);
            commentMapper.updateByPrimaryKey(comment);
            return comment.getLikeCounts();
        }
        else{//没点过赞
            return comment.getLikeCounts();
        }
    }
}
