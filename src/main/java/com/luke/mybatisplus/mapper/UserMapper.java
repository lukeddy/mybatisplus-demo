package com.luke.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luke.mybatisplus.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserMapper extends BaseMapper<User> {

   /**
    * 自定义查询，返回map
    * @param id
    * @return
    */
   Map<String,Object> selectMapById(Long id);

   /**
    * 自定义分页条件查询
    * @param page
    * @param age
    * @return
    */
   Page<User> selectByCustomizePage(@Param("page") Page<User> page,@Param("age") Integer age);
}
