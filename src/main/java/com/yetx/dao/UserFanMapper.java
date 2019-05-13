package com.yetx.dao;

import com.yetx.pojo.UserFan;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFanMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserFan record);

    int insertSelective(UserFan record);

    UserFan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserFan record);

    int updateByPrimaryKey(UserFan record);
}