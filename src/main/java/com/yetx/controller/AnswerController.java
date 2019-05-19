package com.yetx.controller;

import com.yetx.dto.AnswerDTO;
import com.yetx.dto.CommentDTO;
import com.yetx.pojo.Answer;
import com.yetx.service.AnswerService;
import com.yetx.utils.ResultVOUtils;
import com.yetx.vo.CommentVO;
import com.yetx.vo.PageVO;
import com.yetx.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @GetMapping("/all/quality")
    public ResultVO findAllAnswersOrderByQual(@RequestParam("questionId") String questionId,
                                             @RequestParam(value = "staPage",required = false,defaultValue = "1") Integer staPage,
                                             @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize){
        return ResultVOUtils.success(answerService.findAllAnswersOrderByZan(questionId,staPage,pageSize));
    }
    @GetMapping("/all/time")
    public ResultVO findAllAnswerOrderByTime(@RequestParam("questionId") String questionId,
                                             @RequestParam(value = "staPage",required = false,defaultValue = "1") Integer staPage,
                                             @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize){
        return ResultVOUtils.success(answerService.findAllAnswersOrderByTime(questionId,staPage,pageSize));
    }
    @PostMapping("")
    public ResultVO uploadNewAnswer(@RequestHeader("token")String token,
                                    @RequestBody AnswerDTO answerDTO){
        return ResultVOUtils.success(answerService.uploadAnswer(token,answerDTO));
    }
    @PutMapping("")
    public ResultVO updateAnswer(@RequestHeader("token")String token,
                                 @RequestBody AnswerDTO answerDTO){
        return ResultVOUtils.success(answerService.updateAnswer(token,answerDTO));
    }
    @DeleteMapping("")
    public ResultVO deleteAnswer(@RequestHeader("token")String token,
                                 @RequestParam("answerId")String answerId){
        return ResultVOUtils.success(answerService.deleteAnswer(token,answerId));
    }
    @PutMapping("/zan")
    public ResultVO zanAnswer(@RequestHeader("token")String token,
                              @RequestParam("answerId")String answerId){
        return ResultVOUtils.success(answerService.zanAnswer(token,answerId));
    }
    @DeleteMapping("/zan")
    public ResultVO diszanAnswer(@RequestHeader("token")String token,
                                 @RequestParam("answerId")String answerId){
        return ResultVOUtils.success(answerService.diszanAnswer(token,answerId));
    }


    @GetMapping("/comment")
    public ResultVO queryAllCommentByAnswerId(@RequestParam("answerId")String answerId){
        return ResultVOUtils.success(answerService.findCommentsByAnswerId(answerId));
    }

    @PostMapping("/comment")
    public ResultVO commentAnswerOrComment(@RequestHeader("token")String token,
                                           @RequestBody CommentDTO commentDTO){
        return ResultVOUtils.success(answerService.commentAnswer(token,commentDTO));
    }
    @DeleteMapping("/comment")
    public ResultVO deleteComment(@RequestHeader("token")String token,
                                  @RequestParam("commentId")String commentId){
        return ResultVOUtils.success(answerService.deleteComment(token,commentId));
    }
    @PutMapping("/comment/zan")
    public ResultVO zanAnswerComment(@RequestHeader("token")String token,
                              @RequestParam("commentId")String commentId){
        return ResultVOUtils.success(answerService.zanAnswerComment(token,commentId));
    }
    @DeleteMapping("/comment/diszan")
    public ResultVO diszanAnswerComment(@RequestHeader("token")String token,
                                 @RequestParam("commentId")String commentId){
        return ResultVOUtils.success(answerService.diszanAnswerComment(token,commentId));
    }

}
