package com.luke.mybatisplus;

import com.luke.mybatisplus.mapper.UserMapper;
import com.luke.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MyBatisTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList(){
      List<User> userList=  userMapper.selectList(null);
      userList.forEach(System.out::println);
    }

    @Test
    public void testInsert(){
        User user=new User();
        user.setName("zhangsan");
        user.setEmail("zhangsan@qq.com");
        user.setAge(20);
        int result= userMapper.insert(user);
        Assert.isTrue(result>0,"插入失败");
        //查看雪花算法生成的ID
        System.out.println(user.getId());
    }

    @Test
    public void testDelete(){
      int result=  userMapper.deleteById(111);
      Assert.isTrue(result<=0,"成功删除。。。");
      int result2=userMapper.deleteById(1559490027321417729l);
      Assert.isTrue(result2<0,"成功删除！");

      Map<String,Object> map=new HashMap<>();
      map.put("name","zhangsan");
      map.put("email","zhangsan@qq.com");
      int result3=userMapper.deleteByMap(map);
      Assert.isTrue(result3<=0,"成功删除");

      List<Long> idList= Arrays.asList(100l,200l,300l);
      int result4=userMapper.deleteBatchIds(idList);
      Assert.isTrue(result4<=0,"成功删除");
    }

    @Test
    public void testUpdate(){
        User user=new User();
        user.setId(1l);
        user.setName("wangwu");
        user.setEmail("wangwu@gmail.com");
       int result= userMapper.updateById(user);
       Assert.isTrue(result>0,"更新失败");
    }


}
