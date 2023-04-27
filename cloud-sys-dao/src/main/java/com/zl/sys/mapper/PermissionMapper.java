package com.zl.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.sys.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-27
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    //查询id是否存在
    @Select("select * from permission where id=#{id}")
    public Permission findPermissionById(String id);

    //查询父菜单下的子菜单
    @Select("select * from permission where parent_id=#{parentId} order by ordernum desc ")
    public List<Permission> findPermissionChildren(String parentId);

    //查询父菜单id
    @Select("select id from permission where parent_id='0'")
    public List<String> findIdList();

}
