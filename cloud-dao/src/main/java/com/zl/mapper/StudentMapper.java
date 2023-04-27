package com.zl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.entity.Student;
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
public interface StudentMapper extends BaseMapper<Student> {
    @Select("select * from student where uid=#{uid}")
    public Student findStudentByUid(String uid);
}
