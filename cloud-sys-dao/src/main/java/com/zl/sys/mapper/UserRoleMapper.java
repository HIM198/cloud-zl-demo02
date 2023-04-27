package com.zl.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.sys.entity.UserRole;
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
public interface UserRoleMapper extends BaseMapper<UserRole> {
    //查询该用户的角色id
    @Select("select role_id from user_role where uid=#{uid}")
    public Integer findRidByUid(Integer uid);
}
