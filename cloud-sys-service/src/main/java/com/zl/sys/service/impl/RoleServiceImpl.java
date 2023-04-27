package com.zl.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.sys.entity.Role;
import com.zl.sys.entity.RolePermission;
import com.zl.sys.mapper.RoleMapper;
import com.zl.sys.service.RolePermissionService;
import com.zl.sys.service.RoleService;
import com.zl.vo.SearchVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private RolePermissionService rolePermissionService;

    @Override
    public String checkData(Role role) {
        Role dbRole = baseMapper.findRoleByName(role.getName());
        if (dbRole!=null){
            return "角色名称已存在！";
        }
        return null;
    }

    @Override
    public IPage<Role> findPage(SearchVo searchVo) {
        Page<Role> page = new Page<>(searchVo.getPageNumber(),searchVo.getPageCount());

        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(searchVo.getValueOne())){
            wrapper.like("name",searchVo.getValueOne());
        }

        return baseMapper.selectPage(page,wrapper);
    }

    @Override
    public List<Role> findAll() {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("status","ACTIVE");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public boolean deleteRole(String id) {
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("rid",id);
        //删除该角色下的菜单
        rolePermissionService.remove(wrapper);

        //删除角色
        return baseMapper.deleteById(id)==1;
    }
}
