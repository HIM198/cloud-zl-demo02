package com.zl.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.sys.entity.RolePermission;
import com.zl.sys.mapper.RolePermissionMapper;
import com.zl.sys.service.RolePermissionService;
import org.springframework.stereotype.Service;

import java.util.Date;
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
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    @Override
    public boolean saveRolePermission(Integer rid, String pids) {
        //删除原有的关联菜单
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("rid",rid);
        baseMapper.delete(wrapper);

        String[] arr = pids.split(",");

        for (String pid : arr) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setCreateTime(new Date());
            rolePermission.setPid(pid);
            rolePermission.setRid(rid);

            baseMapper.insert(rolePermission);
        }
        return true;
    }

    @Override
    public List<String> findMenuId(Integer rid) {
        return baseMapper.pidList(rid);
    }

    @Override
    public List<String> findMenuNotHaveParentId(Integer rid) {
        return baseMapper.pidListAndNotParentId(rid);
    }
}
