package com.zl.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.entity.User;
import com.zl.sys.entity.Permission;
import com.zl.sys.entity.UserRole;
import com.zl.vo.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-27
 */
public interface UserRoleService extends IService<UserRole> {
    //分配权限
    public boolean assignUserRole(Integer uid,Integer rid);
    //查询用户当前的角色
    public UserRole findUserRoleByUid(Integer uid);
    //查询该用户菜单权限
    public List<Permission> findPermission(Integer uid);
    //通过角色id查询用户
    public List<User> findUserByRid(String rid);
}
