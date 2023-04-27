package com.zl.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.sys.entity.Role;
import com.zl.vo.Result;
import com.zl.vo.SearchVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-27
 */
public interface RoleService extends IService<Role> {
    //校验数据
    public String checkData(Role role);
    //列表模糊分页查询
    public IPage<Role> findPage(SearchVo searchVo);
    //查询所有角色
    public List<Role> findAll();
    //删除角色
    public boolean deleteRole(String id);
}
