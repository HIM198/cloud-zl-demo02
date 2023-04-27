package com.zl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    //查询当前账号是否存在
    @Select("select * from user where name =#{name}")
    public User findUserByName(String name);

}
