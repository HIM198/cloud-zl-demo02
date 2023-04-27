package com.zl.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.sys.entity.Permission;
import com.zl.sys.mapper.PermissionMapper;
import com.zl.sys.service.PermissionService;
import com.zl.vo.SearchVo;
import com.zl.vo.TreeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-27
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public String checkData(Permission permission) {
        Permission dbPermission = baseMapper.findPermissionById(permission.getId());
        if (dbPermission!=null){
            return "菜单编号已存在!";
        }
        return null;
    }

    @Override
    public List<Permission> findAll(SearchVo searchVo) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id","0")
                .like(StringUtils.isNotEmpty(searchVo.getValueOne()),"title",searchVo.getValueOne())
                .orderByAsc("ordernum");
        List<Permission> list = baseMapper.selectList(wrapper);
        for (Permission thisPermission : list) {
            //查询父菜单下的子菜单
            List<Permission> permissionChildren = baseMapper.findPermissionChildren(thisPermission.getId());
            if (!permissionChildren.isEmpty()){
                thisPermission.setChildren(permissionChildren);
            }
        }
        return list;
    }

    @Override
    public List<TreeVo> findTree() {
        ArrayList<TreeVo> treeList = new ArrayList<>();

        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        List<Permission> list = baseMapper.selectList(wrapper);
        if (!list.isEmpty()){
            for (Permission thisPermission : list) {
                TreeVo treeVo = new TreeVo();
                treeVo.setId(thisPermission.getId());
                treeVo.setLabel(thisPermission.getTitle());

                //查询父菜单下的子菜单
                List<Permission> permissionChildren = baseMapper.findPermissionChildren(thisPermission.getId());
                ArrayList<TreeVo> childrenList = new ArrayList<>();
                if (!permissionChildren.isEmpty()){
                    for (Permission thisSubPermission : permissionChildren) {
                        TreeVo subTreeVo = new TreeVo();
                        subTreeVo.setId(thisSubPermission.getId());
                        subTreeVo.setLabel(thisSubPermission.getTitle());
                        childrenList.add(subTreeVo);
                    }
                }
                treeVo.setChildren(childrenList);
                treeList.add(treeVo);
            }
        }
        return treeList;
    }

    @Override
    public List<String> findIdList() {
        return baseMapper.findIdList();
    }
}
