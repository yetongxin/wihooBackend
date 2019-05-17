package com.yetx.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yetx.enums.AuthErrorEnum;
import com.yetx.utils.RedisOperator;
import com.yetx.utils.ResultVOUtils;
import com.yetx.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;


public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisOperator redis;
    private Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
    /**
     * 返回 false：请求被拦截，返回
     * 返回 true ：请求OK，可以继续执行，放行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        logger.info("传进来的token:{}",token);
        if(token==null||StringUtils.isEmpty((token))){
            returnErrorResponse(response, ResultVOUtils.fail(AuthErrorEnum.TOKEN_NULL));
            logger.error("传进来的token为null");
            return false;
        }
        else{
            String redisres = redis.get("TOKEN:"+token);
            if(redisres==null||StringUtils.isEmpty(redisres)){
                logger.info("token找不到，可能已经过期");
                returnErrorResponse(response,ResultVOUtils.fail(AuthErrorEnum.TOKEN_NOT_FIND));
                return false;
            }
        }

        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public void returnErrorResponse(HttpServletResponse response, ResultVO result)
            throws IOException, UnsupportedEncodingException {
        OutputStream out=null;
        try{
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue).getBytes("utf-8"));
            out.flush();
        } finally{
            if(out!=null){
                out.close();
            }
        }
    }
}
