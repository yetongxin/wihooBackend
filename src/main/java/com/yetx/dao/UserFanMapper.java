package com.yetx.dao;

import com.yetx.pojo.UserFan;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFanMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserFan record);

    int insertSelective(UserFan record);

    UserFan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserFan record);

    int updateByPrimaryKey(UserFan record);

    @Select("select COUNT(*) from user_fan where fan_id=#{followId} and user_id=#{beFollowedId}")
    int countIfFollow(String followId,String beFollowedId);

    @Delete("delete from user_fan where fan_id=#{followId} and user_id=#{beFollowedId}")
    int deleteUserFan(String followId,String beFollowedId);

}