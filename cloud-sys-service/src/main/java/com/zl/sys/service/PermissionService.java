package com.zl.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.sys.entity.Permission;
import com.zl.vo.SearchVo;
import com.zl.vo.TreeVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-27
 */
public interface PermissionService extends IService<Permission> {
    //校验数据
    public String checkData(Permission permission);
    //菜单列表查询
    public List<Permission> findAll(SearchVo searchVo);
    //查询树列表
    public List<TreeVo> findTree();
    //查询父菜单id
    public List<String> findIdList();
}
