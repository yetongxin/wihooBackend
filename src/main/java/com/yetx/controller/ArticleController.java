package com.yetx.controller;

import com.yetx.dto.CommentDTO;
import com.yetx.pojo.Article;
import com.yetx.pojo.ArticleComment;
import com.yetx.pojo.Comment;
import com.yetx.service.ArticleService;
import com.yetx.utils.ResultVOUtils;
import com.yetx.vo.CommentVO;
import com.yetx.vo.PageVO;
import com.yetx.vo.ResultVO;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @GetMapping("/all/popu")
    public ResultVO getAllArticle(@RequestParam Integer staPage,@RequestParam Integer pageSize){
        PageVO pageVO = articleService.findAllArticleByPopularity(staPage,pageSize);
        return ResultVOUtils.success(pageVO);
    }
    @GetMapping("/comments")
    public ResultVO getAllComment(@RequestParam String articleId){
        List<CommentVO> articleComments = articleService.findAllCommentByArticleId(articleId);
        return ResultVOUtils.success(articleComments);
    }

    //以下需要验证身份
    @PostMapping("/upload")
    public ResultVO saveArticle(@RequestHeader("token") String token, @RequestBody Article article){
        Article articleRes = articleService.saveArticle(token,article);
        return ResultVOUtils.success(articleRes);
    }
    @DeleteMapping("/delete")
    public ResultVO deleteArticle(@RequestHeader("token") String token, @RequestParam String  articleId){
        Boolean res = articleService.deleteArticle(token,articleId);
        return ResultVOUtils.success(res);
    }
    @PostMapping("/collect")
    public ResultVO collectArticle(@RequestHeader("token") String token, @RequestParam String articleId){
        Boolean isDone = articleService.collectArticle(token,articleId);
        return ResultVOUtils.success(isDone);
    }
    @PostMapping("/discollect")
    public ResultVO discollectArticle(@RequestHeader("token") String token, @RequestParam String articleId){
        Boolean isDone = articleService.disCollectArticle(token,articleId);
        return ResultVOUtils.success(isDone);
    }
    @PostMapping("/zan")
    public ResultVO zanArticle(@RequestHeader("token") String token,@RequestParam String articleId){
        Integer res = articleService.zanArticle(token,articleId);
        return ResultVOUtils.success(res);
    }
    @PostMapping("/diszan")
    public ResultVO disZanArticle(@RequestHeader("token") String token,@RequestParam String articleId){
        Integer res = articleService.disZanArticle(token,articleId);
        return ResultVOUtils.success(res);
    }

    @PostMapping("/comment")
    public ResultVO commentArticle(@RequestHeader("token")String token,@RequestBody CommentDTO commentDTO){
        String commentId = articleService.commentArticle(token,commentDTO);
        return ResultVOUtils.success(commentId);
    }

    @DeleteMapping("/comment")
    public  ResultVO deleteCommentArticle(@RequestHeader("token")String token,@RequestParam String articleCommentId){
        Boolean isDone = articleService.deleteCommentArticle(token,articleCommentId);
        return ResultVOUtils.success(isDone);
    }
    @RequestMapping("/test")
    public ResultVO test(){
        int a = 10;
        int b= 0;
        int c = a/b;
        return ResultVOUtils.success("ok");
    }
}
