package com.yetx.utils;

import com.yetx.enums.ErrorEnum;
import com.yetx.enums.StatusEnum;
import com.yetx.vo.ResultVO;

public class ResultVOUtils {
    public static ResultVO success(Object data){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(data);
        resultVO.setCode(200);
        resultVO.setMsg("成功");
        return resultVO;
    }
    public static ResultVO success(Object data,ErrorEnum statusEnum){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(data);
        resultVO.setCode(statusEnum.getCode());
        resultVO.setMsg(statusEnum.getMsg());
        return resultVO;
    }
    public static ResultVO fail(){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(null);
        resultVO.setCode(500);
        resultVO.setMsg("请求失败");
        return resultVO;
    }
    public static ResultVO fail(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(null);
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
    public static ResultVO fail(ErrorEnum statusEnum){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(null);
        resultVO.setCode(statusEnum.getCode());
        resultVO.setMsg(statusEnum.getMsg());
        return resultVO;
    }
}
