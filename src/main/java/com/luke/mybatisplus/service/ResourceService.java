package com.luke.mybatisplus.service;

import com.luke.mybatisplus.entity.Resource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luke.mybatisplus.vo.ResourceVo;

import java.util.List;

/**
* @author luke
* @description 针对表【resource(资源表)】的数据库操作Service
* @createDate 2022-08-17 16:55:23
*/
public interface ResourceService extends IService<Resource> {
    List<ResourceVo> getResourceByRoleId(Long roleId);
}
