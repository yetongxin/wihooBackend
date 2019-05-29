package com.yetx.service;

import com.yetx.dto.ArticleDTO;
import com.yetx.dto.CommentDTO;
import com.yetx.pojo.Article;
import com.yetx.pojo.ArticleComment;
import com.yetx.pojo.Comment;
import com.yetx.vo.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleService {

    public DraftVO findUserDraft(String token);

    public PageVO findAllArticleByPopularity(int staPage,int pageSize);

    PageVO findAllArticleByTime(int staPage, int pageSize);

    public List<CommentVO> findAllCommentByArticleId(String articleId);

    /*
    上传保存文章、保存到草稿箱
    点赞文章 //需要一个表防止多次点赞
    收藏文章
    评论文章
    评论回答*/

    /**
     * 保存文章，包含修改与删除
     * @return
     */
    public Article saveArticle(String token,Article article);

    String uploadArticle(String token, ArticleDTO article);

    public ArticleDetailVO findArticleDetailByAId(String token,String articleId);
    //TODO: 修改article表中的openid字段为userId
    String updateArticle(String token, ArticleDTO articleDTO);

    /**
     * 删除文章
     */
    Boolean deleteArticle(String token, String ArticleId);

    /**
     * 收藏文章
     */

    public Boolean collectArticle(String token, String articleId);

    /**
     * 取消收藏文章
     * @param articleId
     * @return
     */
    public Boolean disCollectArticle(String token, String articleId);

    /**
     * 评论文章
     */
    String commentArticle(String token, CommentDTO articleComment);

    /**
     * 删除评论
     */
    Boolean deleteCommentArticle(String token, String articleCommentId);

    /**
     * 点赞文章
     */
    Integer zanArticle(String token, String articleId);

    /**
     * 取消赞
     */
    Integer disZanArticle(String token, String articleId);

    /**
     * 用户按下鼠标后
     *
     * 1.前端禁用或者改变点赞按钮，变成不能再点的状态
     *
     * 2.向后台发送数据
     *
     * 3.后台验证用户是否登录
     *
     * 4.后台验证用户是否已经赞过（查询数据库需要时间）如果已经赞过，跳过5 6步 直接返回数据
     *
     * 5.主题被赞次数+1
     *
     * 6.记录用户已经赞过
     *
     * 7.向前台返回数据
     */



}
