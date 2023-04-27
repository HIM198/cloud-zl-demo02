package com.zl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-30
 */
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
    @Select("select * from teacher where uid=#{uid}")
    public Teacher findTeacherByUid(String uid);
}
