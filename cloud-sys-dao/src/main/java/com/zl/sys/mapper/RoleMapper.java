package com.zl.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.sys.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-27
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    //查询角色名称是否重复
    @Select("select * from role where name=#{name}")
    public Role findRoleByName(String name);
}
