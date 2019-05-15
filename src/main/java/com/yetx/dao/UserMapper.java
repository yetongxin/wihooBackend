package com.yetx.dao;

import com.yetx.dto.UserDTO;
import com.yetx.pojo.Article;
import com.yetx.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserMapper {

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByOpenid(String openid);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    @Update("update user set nickname=#{nickname},avatar=#{avatar} where openid=#{openid}")
    int updateNickNameAndAvatarByOpenid(@Param("openid") String openid,
                                        @Param("nickname") String nickname,
                                        @Param("avatar") String avatar);
    UserDTO selectFollowsByOpenid(String openid);

//    UserDTO selectAllArticleByOpenid(String openid);
//    UserDTO selectAllQuestionByOpenid(String openid);
//    UserDTO selectAllAnswerByOpenid(String openid);
}