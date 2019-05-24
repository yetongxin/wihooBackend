package com.yetx.controller;

import com.yetx.dto.UserDTO;
import com.yetx.enums.StatusEnum;
import com.yetx.pojo.User;
import com.yetx.service.RedisService;
import com.yetx.service.UserService;
import com.yetx.utils.ResultVOUtils;
import com.yetx.vo.PageVO;
import com.yetx.vo.ResultVO;

import com.yetx.vo.UserLoginStatusVO;
import io.netty.util.internal.StringUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResultVO doRegister(@RequestHeader("token") String token,
                            @RequestParam("nickname") String nickname,
                            @RequestParam("avatar") String avatar){
        System.out.println("进来/user/register");
        User user = userService.register(token,nickname,avatar);
        return ResultVOUtils.success(user);
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResultVO doLogin(@RequestParam("code") String code){
        System.out.println("进来了");
        UserLoginStatusVO userLoginStatusVO = userService.login(code);
        if(userLoginStatusVO!=null)
            return ResultVOUtils.success(userLoginStatusVO);
        return ResultVOUtils.fail();
    }
    @RequestMapping(value = "/nickname",method = RequestMethod.PUT)
    public ResultVO changeNickname(@RequestHeader("token") String token, @RequestParam("nickname") String nickname) throws Exception {
        String updateres = userService.updateNickname(token,nickname);
        if(!StringUtils.isEmpty(updateres))
            return ResultVOUtils.success(updateres);
        return ResultVOUtils.fail();
    }
    @RequestMapping(value = "/avatar",method = RequestMethod.POST)
    public ResultVO changeAvatar(@RequestHeader("token") String token, @RequestParam("file") MultipartFile[] files){

        String updateres = userService.updateAvatar(token,files);
        if(!StringUtils.isEmpty(updateres))
            return ResultVOUtils.success(updateres);
        return ResultVOUtils.fail();
    }

    /**
     * 查看用户的所有关注的人
     * @param token
     * @return
     */
    @RequestMapping(value = "/follows",method = RequestMethod.GET)
    public ResultVO getAllFollow(@RequestHeader("token") String token){
        UserDTO userDTO = userService.findAllFollow(token);
        return ResultVOUtils.success(userDTO.getFollowUsers());
    }


    @RequestMapping(value = "/articles",method = RequestMethod.GET)
    public ResultVO getMyAllArticle(@RequestHeader("token") String token,
                                    @RequestParam("staPage")Integer staPage,
                                    @RequestParam("pageSize")Integer pageSize){
        PageVO pageVO = userService.findAllArticle(token,staPage,pageSize);
        return ResultVOUtils.success(pageVO);
    }
    @RequestMapping(value = "/questions",method = RequestMethod.GET)
    public ResultVO getMyAllQuestion(@RequestHeader("token") String token,
                                     @RequestParam("staPage")Integer staPage,
                                     @RequestParam("pageSize")Integer pageSize){
        PageVO pageVO = userService.findAllQuestion(token,staPage,pageSize);
        return ResultVOUtils.success(pageVO);
    }
    @RequestMapping(value = "/answers",method = RequestMethod.GET)
    public ResultVO getMyAllAnswer(@RequestHeader("token") String token,
                                   @RequestParam("staPage")Integer staPage,
                                   @RequestParam("pageSize")Integer pageSize){
        PageVO pageVO = userService.findAllAnswer(token,staPage,pageSize);
        return ResultVOUtils.success(pageVO);
    }
    @GetMapping("/collectAnswer")
    public ResultVO queryCollectAnswer(@RequestHeader("token") String token){
        return ResultVOUtils.success(userService.findAllCollectAnswer(token));
    }
    @GetMapping("/focusQuestion")
    public ResultVO queryFocusQuestion(@RequestHeader("token") String token){
        return ResultVOUtils.success(userService.findAllFocusQuestion(token));
    }
}
