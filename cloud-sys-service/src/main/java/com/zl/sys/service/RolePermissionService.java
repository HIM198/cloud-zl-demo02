package com.zl.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.sys.entity.RolePermission;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-27
 */
public interface RolePermissionService extends IService<RolePermission> {
    //新增角色菜单
    public boolean saveRolePermission(Integer rid,String pids);
    //查询该角色当前的菜单
    public List<String> findMenuId(Integer rid);
    //查询该角色当前的菜单不包含父菜单
    public List<String> findMenuNotHaveParentId(Integer rid);
}
