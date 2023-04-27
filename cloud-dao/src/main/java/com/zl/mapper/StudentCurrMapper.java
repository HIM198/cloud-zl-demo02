package com.zl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.entity.StudentCurr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-30
 */
@Mapper
public interface StudentCurrMapper extends BaseMapper<StudentCurr> {
    @Select("select sid from student_curr where cid=#{cid}")
    public List<Integer> findStudentCurr(String cid);
}
