package com.yetx.dao;


import com.yetx.pojo.Comment;
import com.yetx.vo.CommentVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentTest {
    @Autowired
    private CommentMapper mapper;
    @Test
    public void selectCommentsByArticleIdTest(){
        List<Comment> list = mapper.selectCommentsByArticleId("0aeedac9-567b-4151-afeb-1d1e20bacd4f");
        //Assert.assertEquals(2,list.size());
//        System.out.println(list);
        List<Comment> list1 = mapper.selectCommentsByCommentId("0000001");
        //Assert.assertEquals(2,list1.size());
//        System.out.println(list1);

//        select c1.*,c2.* from comment c1 left join comment c2 on c2.parent_id=c1.id and c2.parent_type=1
//        where c1.parent_type=2 and c1.parent_id="0aeedac9-567b-4151-afeb-1d1e20bacd4f";

        List<CommentVO> list2 = mapper.selectCommentsvoByArticleId("0aeedac9-567b-4151-afeb-1d1e20bacd4f");
        System.out.println(list2);
    }

}
