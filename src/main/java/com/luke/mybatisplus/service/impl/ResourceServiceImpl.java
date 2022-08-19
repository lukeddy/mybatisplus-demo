package com.luke.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luke.mybatisplus.entity.Resource;
import com.luke.mybatisplus.service.ResourceService;
import com.luke.mybatisplus.mapper.ResourceMapper;
import com.luke.mybatisplus.vo.ResourceVo;
import com.luke.mybatisplus.vo.TreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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
        query.eq("rr.role_id",roleId)
                .isNull("re.parent_id")
                .orderByAsc("re.sort");
        List<ResourceVo> resourceVoList=baseMapper.selectResource(query);
        resourceVoList.forEach(r->{
            QueryWrapper<Resource> subQuery=Wrappers.query();
            subQuery.eq("rr.role_id",roleId)
                    .eq("re.parent_id",r.getResourceId())
                    .orderByAsc("re.sort");
            List<ResourceVo> subResourceList=baseMapper.selectResource(subQuery);
            if(CollectionUtils.isNotEmpty(subResourceList)){
                r.setSubs(subResourceList);
            }
        });

        return resourceVoList;
    }

    @Override
    public List<TreeVo> getResourceList(Long roleId) {
        if(null!=roleId){
            QueryWrapper<Resource> query=new QueryWrapper<>();
            query.isNull("re.parent_id")
                    .orderByAsc("re.sort");
            List<TreeVo> treeVoList=baseMapper.selectResourceByRoleId(query,roleId);
            treeVoList.forEach(t->{
                t.setChecked(false);
                Long id=t.getId();
                QueryWrapper<Resource> subQuery=Wrappers.query();
                subQuery.eq("re.parent_id",id)
                        .orderByAsc("re.sort");
                List<TreeVo> children=baseMapper.selectResourceByRoleId(subQuery,roleId);
                if(CollectionUtils.isNotEmpty(children)){
                    t.setChildren(children);
                }
            });
            return treeVoList;
        }else {
            LambdaQueryWrapper<Resource> query = new LambdaQueryWrapper<>();
            query.isNull(Resource::getParentId)
                    .orderByAsc(Resource::getSort);
            //先获取一级资源
            List<Resource> resourceList = list(query);
            List<TreeVo> treeVoList = resourceList.stream().map(r -> {
                TreeVo treeVo = new TreeVo();
                treeVo.setId(r.getResourceId());
                treeVo.setTitle(r.getResourceName());

                LambdaQueryWrapper<Resource> subQuery = new LambdaQueryWrapper();
                subQuery.eq(Resource::getParentId, r.getResourceId())
                        .orderByAsc(Resource::getSort);

                //再根据一级资源获取二级资源
                List<Resource> subResources = list(subQuery);
                if (CollectionUtils.isNotEmpty(subResources)) {
                    List<TreeVo> children = subResources.stream().map(sub -> {
                        TreeVo subTreeVo = new TreeVo();
                        subTreeVo.setId(sub.getResourceId());
                        subTreeVo.setTitle(sub.getResourceName());
                        return subTreeVo;
                    }).collect(Collectors.toList());
                    treeVo.setChildren(children);
                }
                return treeVo;
            }).collect(Collectors.toList());

            return treeVoList;
        }
    }

    @Override
    public HashSet<String> convert(List<ResourceVo> resourceVoList) {
        HashSet<String> modules=new HashSet<>();
        resourceVoList.forEach(r->{
            String url=r.getUrl();
            if(StringUtils.isNotBlank(url)){
                modules.add(url.substring(0,url.indexOf("/")));
            }

            List<ResourceVo> subResource=r.getSubs();
            if(CollectionUtils.isNotEmpty(subResource)){
                subResource.forEach(sub->{
                    String subURL=sub.getUrl();
                    if(StringUtils.isNotBlank(subURL)){
                        modules.add(subURL.substring(0,subURL.indexOf("/")));
                    }
                });
            }
        });

        return modules;
    }
}




