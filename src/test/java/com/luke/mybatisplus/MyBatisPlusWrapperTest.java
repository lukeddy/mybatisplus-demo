package com.luke.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.luke.mybatisplus.mapper.UserMapper;
import com.luke.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class MyBatisPlusWrapperTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 测试查询用户名包含a,年龄在20到30之间，邮箱不为空的用户信息
     */
    @Test
    public void testQueryWrapper(){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name","a")
                .between("age",20,30)
                .isNotNull("email");
       List<User> userList= userMapper.selectList(queryWrapper);
       userList.forEach(System.out::println);
    }

    /**
     * 查询用户信息，按照age降序排序，如果年龄相同，则按照id升序排序
     */
    @Test
    public void testQueryWrapper2(){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("age")
                .orderByAsc("id");
        List<User> userList=userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 删除用户名为zhangsan开头的记录
     */
    @Test
    public void testQueryWrapper3(){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name","zhangsan");
        int result=userMapper.delete(queryWrapper);
        System.out.println(result);
    }

    /**
     * 将(年龄大于20并且用户名中包含wu)或者邮箱为null的用户信息修改
     */
    @Test
    public void testQueryWrapper4(){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.gt("age",20)
                .like("name","wu")
                .or()
                .isNull("email");
        User user=new User();
        user.setName("李四");
        user.setEmail("lisi@qq.com");
        int result= userMapper.update(user,queryWrapper);
        System.out.println(result);
    }

    /**
     * 将用户名包含李四并且(年龄大于22或者邮箱为null)的用户信息进行修改
     * lambda中的条件优先执行
     * sql语句: UPDATE user SET name=?, email=? WHERE (name LIKE ? AND (age > ? OR email IS NULL))
     */
    @Test
    public void testQueryWrapper5(){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name","李四")
                .and(i->i.gt("age",22).or().isNull("email"));

        User user=new User();
        user.setName("小明");
        user.setEmail("xiaoming@gmail.com");
        int result=  userMapper.update(user,queryWrapper);
        System.out.println(result);
    }

    /**
     * 查询用户的指定字段：用户名，年龄，邮箱
     */
    @Test
    public void testQueryWrapper6(){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("name","age","email");
        List<Map<String,Object>> userList=userMapper.selectMaps(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 查询id小于等于100的信息
     * 子查询
     */
    @Test
    public void testQueryWrapper7(){
//        QueryWrapper queryWrapper=new QueryWrapper();
//        queryWrapper.lt("id",100l);
//        List<User> userList=userMapper.selectList(queryWrapper);
//        userList.forEach(System.out::println);

        QueryWrapper queryWrapper1=new QueryWrapper();
        queryWrapper1.inSql("id","SELECT id FROM user WHERE id < 100");
        List<User> userList=userMapper.selectList(queryWrapper1);
        userList.forEach(System.out::println);
    }

    /**
     * 条件查询
     */
    @Test
    public void testQueryWrapper8(){
        String name="Wang";
        Integer ageBegin=null;
        Integer ageEnd=100;
        QueryWrapper<User> queryWrapper=new QueryWrapper();
        if(StringUtils.isNotBlank(name)){
            queryWrapper.like("name",name);
        }
        if(null!=ageBegin){
            queryWrapper.ge("age",ageBegin);
        }
        if(null!=ageEnd){
            queryWrapper.le("age",ageEnd);
        }
        List<User> userList=userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void testUpdateWrapper(){
        UpdateWrapper<User> updateWrapper=new UpdateWrapper();
        updateWrapper.like("name","李")
                .and(i->i.gt("age",20).or().isNull("email"));
        updateWrapper.set("name","王五")
                .set("email","wangwu111@gmail.com");

        int result=userMapper.update(null,updateWrapper);
        System.out.println(result);
    }

    /**
     * 等价testQueryWrapper8
     */
    @Test
    public void testQueryWrapper9(){
        String name="Wang";
        Integer ageBegin=null;
        Integer ageEnd=100;
        QueryWrapper<User> queryWrapper=new QueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(name),"name",name)
                .ge(null!=ageBegin,"age",ageBegin)
                .le(null!=ageEnd,"age",ageEnd);
        List<User> userList=userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 等价testQueryWrapper9
     */
    @Test
    public void testLambdaQueryWrapper(){
        String name="Wang";
        Integer ageBegin=null;
        Integer ageEnd=100;
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(name),User::getName,name)
                .ge(null!=ageBegin,User::getAge,ageBegin)
                .le(null!=ageEnd,User::getAge,ageEnd);
        List<User> userList=userMapper.selectList(lambdaQueryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void testLambdaUpdateWrapper(){
        LambdaUpdateWrapper<User> lambdaUpdateWrapper=new LambdaUpdateWrapper();
        lambdaUpdateWrapper.like(User::getName,"王")
                .and(i->i.gt(User::getAge,21).or().isNull(User::getEmail));
        lambdaUpdateWrapper.set(User::getName,"路西")
                .set(User::getEmail,"luxi@gmail.com");

        int result=userMapper.update(null,lambdaUpdateWrapper);
        System.out.println(result);
    }
}
