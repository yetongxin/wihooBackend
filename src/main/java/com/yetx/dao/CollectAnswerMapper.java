package com.yetx.dao;

import com.yetx.constant.ZanType;
import com.yetx.pojo.CollectAnswer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectAnswerMapper {
    int deleteByPrimaryKey(String id);

    int insert(CollectAnswer record);

    int insertSelective(CollectAnswer record);

    CollectAnswer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CollectAnswer record);

    int updateByPrimaryKey(CollectAnswer record);

    //查询是否已经有回答的赞
    @Select("select COUNT(*) from collect_answer where user_id=#{userId} and answer_id=#{answerId}")
    int countAnswerCollect(@Param("userId")String userId,
                       @Param("answerId")String answerId);
    //删除回答的赞
    @Delete("delete from collect_answer where user_id=#{userId} and answer_id=#{answerId}")
    int deleteAnswerCollect(@Param("userId")String userId,
                        @Param("answerId")String answerId);
}