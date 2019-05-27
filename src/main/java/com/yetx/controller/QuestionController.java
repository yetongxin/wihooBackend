package com.yetx.controller;

import com.yetx.dto.QuestionDTO;
import com.yetx.service.QuestionService;
import com.yetx.utils.ResultVOUtils;
import com.yetx.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    /**
     *     public PageVO findAllQuestions(Integer staPage, Integer pageSize);
     *
     *     public Question uploadQuestion(String token, QuestionDTO questionDTO);
     *
     *     public Boolean deleteQuestion(String token,String questionId);
     *
     *     public List<Question> findQuestionByUserId(String userId);
     *
     *     public List<Question> searchQuestionByKeyWord(String keyword);
     *
     *     public void focusQuestion(String token,String questionId);
     */

    @GetMapping("/all/time")
    public ResultVO queryAllQuestion(@RequestParam Integer staPage,@RequestParam Integer pageSize){
        return ResultVOUtils.success(questionService.findAllQuestions(staPage,pageSize));
    }
    @GetMapping("/all/popu")
    public ResultVO queryTopNQuestion(){
        return ResultVOUtils.success(questionService.findTopNQuestion());
    }
    @PostMapping("")
    public ResultVO uploadQuestion(@RequestHeader("token")String token, @RequestBody QuestionDTO questionDTO){
        return ResultVOUtils.success(questionService.uploadQuestion(token,questionDTO));
    }
    @PutMapping("")
    public ResultVO updateQuestion(@RequestHeader("token")String token,@RequestBody QuestionDTO questionDTO){
        return ResultVOUtils.success(questionService.updateQuestion(token,questionDTO));
    }
    @DeleteMapping("")
    public ResultVO deleteQuestion(@RequestHeader("token")String token,@RequestParam String questionId){
        return ResultVOUtils.success(questionService.deleteQuestion(token,questionId));
    }
    @GetMapping("/user")
    public ResultVO queryUserQuestion(@RequestParam String userId){
        return ResultVOUtils.success(questionService.findQuestionByUserId(userId));
    }
    @GetMapping("/search")
    public ResultVO searchQuestion(@RequestParam String keyword){
        return ResultVOUtils.success(questionService.searchQuestionByKeyWord(keyword));
    }
    @PostMapping("/focus")
    public ResultVO focusQuestion(@RequestHeader("token")String token,@RequestParam String questionId){
        return ResultVOUtils.success(questionService.focusQuestion(token,questionId));
    }
    @DeleteMapping("/focus")
    public ResultVO disFocusQuestion(@RequestHeader("token")String token,@RequestParam String questionId){
        return ResultVOUtils.success(questionService.disFocusQuestion(token,questionId));
    }
}
