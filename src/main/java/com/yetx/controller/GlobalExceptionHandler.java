package com.yetx.controller;

import com.yetx.exception.MyException;
import com.yetx.utils.ResultVOUtils;
import com.yetx.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(MyException.class)
    @ResponseBody
    public ResultVO handleException(MyException e){
        logger.error("出现异常：{}",e);
        return ResultVOUtils.fail(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultVO unknownException(Exception e){
        logger.error("出现未知异常：{}",e);
        return ResultVOUtils.fail(-1,"未知异常,请联系我们");
    }
}
