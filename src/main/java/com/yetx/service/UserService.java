package com.yetx.service;


import com.github.pagehelper.PageInfo;
import com.yetx.dto.UserDTO;
import com.yetx.pojo.Answer;
import com.yetx.pojo.Article;
import com.yetx.pojo.Question;
import com.yetx.pojo.User;
import com.yetx.vo.PageVO;
import com.yetx.vo.UserLoginStatusVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    public User register(String token, String nickname, String avatar);
    public UserLoginStatusVO login(String code);
    public List<User> findAllUser();
    public User findByOpenid(String openid);
    public String updateNickname(String token,String nickname) throws Exception;
    public String updateAvatar(String token, MultipartFile[] files);
    /**
     * 查看关注的所有人
     */
    public UserDTO findAllFollow(String token);
    /**
     * 查看所有文章,问题，回答
     */
    public PageVO findAllArticle(String token, Integer staPage, Integer pageSize);
    public PageVO findAllQuestion(String token, Integer staPage, Integer pageSize);
    public PageVO findAllAnswer(String token, Integer staPage, Integer pageSize);

    //查看收藏的回答
    List<Answer> findAllCollectAnswer(String token);

    List<Question> findAllFocusQuestion(String token);
}
