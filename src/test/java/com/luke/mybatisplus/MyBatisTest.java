package com.luke.mybatisplus;

import com.luke.mybatisplus.mapper.UserMapper;
import com.luke.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;

@SpringBootTest
public class MyBatisTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList(){
      List<User> userList=  userMapper.selectList(null);
      userList.forEach(System.out::println);
    }

}
