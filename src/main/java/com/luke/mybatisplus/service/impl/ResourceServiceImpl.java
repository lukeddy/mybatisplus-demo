package com.luke.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luke.mybatisplus.entity.Resource;
import com.luke.mybatisplus.service.ResourceService;
import com.luke.mybatisplus.mapper.ResourceMapper;
import com.luke.mybatisplus.vo.ResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author luke
* @description 针对表【resource(资源表)】的数据库操作Service实现
* @createDate 2022-08-17 16:55:23
*/
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource>
    implements ResourceService{

    @Override
    public List<ResourceVo> getResourceByRoleId(Long roleId) {
        QueryWrapper<Resource> query= Wrappers.query();
        query.eq("rr.role_id",roleId).isNull("re.parent_id");
        List<ResourceVo> resourceVoList=baseMapper.listResource(query);
        resourceVoList.forEach(r->{
            QueryWrapper<Resource> subQuery=Wrappers.query();
            subQuery.eq("rr.role_id",roleId)
                    .eq("re.parent_id",r.getResourceId());
            List<ResourceVo> subResourceList=baseMapper.listResource(subQuery);
            if(CollectionUtils.isNotEmpty(subResourceList)){
                r.setSubs(subResourceList);
            }
        });

        return resourceVoList;
    }
}




