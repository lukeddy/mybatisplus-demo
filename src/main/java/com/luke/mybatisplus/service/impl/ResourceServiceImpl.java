package com.luke.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luke.mybatisplus.entity.Resource;
import com.luke.mybatisplus.service.ResourceService;
import com.luke.mybatisplus.mapper.ResourceMapper;
import org.springframework.stereotype.Service;

/**
* @author luke
* @description 针对表【resource(资源表)】的数据库操作Service实现
* @createDate 2022-08-17 16:55:23
*/
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource>
    implements ResourceService{

}




