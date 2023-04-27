package com.zl.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.entity.User;
import com.zl.mapper.UserMapper;
import com.zl.sys.entity.Permission;
import com.zl.sys.entity.UserRole;
import com.zl.sys.mapper.RolePermissionMapper;
import com.zl.sys.mapper.UserRoleMapper;
import com.zl.sys.service.PermissionService;
import com.zl.sys.service.UserRoleService;
import com.zl.vo.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-27
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;
    @Resource
    private PermissionService permissionService;
    @Resource
    private UserMapper userMapper;

    @Override
    public boolean assignUserRole(Integer uid, Integer rid) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",uid);
        //删除原有权限
        baseMapper.delete(wrapper);

        UserRole userRole = new UserRole();
        userRole.setRoleId(rid);
        userRole.setUid(uid);
        userRole.setCreateTime(new Date());
        //新增更新后的权限;
        return baseMapper.insert(userRole)==1;
    }

    @Override
    public UserRole findUserRoleByUid(Integer uid) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",uid);
        List<UserRole> list = baseMapper.selectList(wrapper);
        if (!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Permission> findPermission(Integer uid) {
        ArrayList<Permission> list = new ArrayList<>();
        ArrayList<Permission> listAll = new ArrayList<>();
        //角色id
        Integer rid = userRoleMapper.findRidByUid(uid);

        //查询菜单id
        List<String> pidList = rolePermissionMapper.pidList(rid);

        if (!pidList.isEmpty()){
            for (String thisPid : pidList) {
                //所有菜单对象
                Permission dbPermission = permissionService.getById(thisPid);
                listAll.add(dbPermission);
                //把父菜单放入集合中
                if (dbPermission.getParentId().equals("0")&&dbPermission.getStatus().equals("ACTIVE")){
                    list.add(dbPermission);
                }
            }
        }
        //查询父菜单下的子菜单
        for (Permission thisParent : list) {
            ArrayList<Permission> subList = new ArrayList<>();
            for (Permission thisSub : listAll) {
                if (thisParent.getId().equals(thisSub.getParentId()) && thisSub.getStatus().equals("ACTIVE")){
                    subList.add(thisSub);
                }
            }
            //使用stream流对集合中的ordernum自动进行排序
            List<Permission> collect = subList.stream().sorted(Comparator.comparing(Permission::getOrdernum)).collect(Collectors.toList());
            thisParent.setChildren(collect);
        }

        //将父菜单也进行排序
        return list.stream().sorted(Comparator.comparing(Permission::getOrdernum)).collect(Collectors.toList());
    }

    @Override
    public List<User> findUserByRid(String rid) {
        ArrayList<User> userList = new ArrayList<>();
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",rid);
        List<UserRole> list = baseMapper.selectList(wrapper);
        if (!list.isEmpty()){
            for (UserRole thisUserRole : list) {
                userList.add(userMapper.selectById(thisUserRole.getUid()));
            }
        }
        return userList;
    }
}
