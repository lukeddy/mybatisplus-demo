package com.luke.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.luke.mybatisplus.entity.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luke.mybatisplus.vo.ResourceVo;
import com.luke.mybatisplus.vo.TreeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author luke
* @description 针对表【resource(资源表)】的数据库操作Mapper
* @createDate 2022-08-17 16:55:23
* @Entity com.luke.mybatisplus.entity.Resource
*/
public interface ResourceMapper extends BaseMapper<Resource> {
    /**
     * 查询用户可访问资源
     * @param wrapper
     * @return
     */
   List<ResourceVo> selectResource(@Param(Constants.WRAPPER) Wrapper<Resource> wrapper);

    /**
     * 根据角色ID加载资源
     * @param roleId
     * @return
     */
   List<TreeVo> selectResourceByRoleId(@Param(Constants.WRAPPER) Wrapper<Resource> wrapper,
                                       @Param("roleId") Long roleId);
}




