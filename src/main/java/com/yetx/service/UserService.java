package com.yetx.service;


import com.github.pagehelper.PageInfo;
import com.yetx.dto.OtherUserDTO;
import com.yetx.dto.UserDTO;
import com.yetx.pojo.*;
import com.yetx.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    public User register(String token, String nickname, String avatar);
    public UserLoginStatusVO login(String code);
    public List<User> findAllUser();
    public User findByOpenid(String openid);
    public String updateNickname(String token,String nickname) throws Exception;
    public String updateAvatar(String token, MultipartFile[] files);
    public String updateBgimg(String token, MultipartFile[] files);
    /**
     * 查看关注的所有人
     */
    public List<OtherUserDTO> findAllFollow(String token);
    /**
     * 查看所有文章,问题，回答
     */
    public PageVO findAllArticle(String token, Integer staPage, Integer pageSize);
    public PageVO findAllQuestion(String token, Integer staPage, Integer pageSize);
    public PageVO findAllAnswer(String token, Integer staPage, Integer pageSize);

    //查看收藏的回答
    List<CollectAnswerVO> findAllCollectAnswer(String token);

    List<QuestionVO> findAllFocusQuestion(String token);

    OtherUserVO findOtherUsrByUserId(String userId);

    public PageVO findOtherArticle(String userId,Integer staPage,Integer pageSize);
    public PageVO findOtherAnswer(String userId,Integer staPage,Integer pageSize);
    public PageVO findOtherQuestion(String userId,Integer staPage,Integer pageSize);
    public List<CollectAnswerVO> findOtherCollectAnswer(String userId);


    Boolean handlefollowOther(String token, String otherUserId, Integer status);
}
