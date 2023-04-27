package com.zl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.entity.Case;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-25
 */
@Mapper
public interface CaseMapper extends BaseMapper<Case> {
    @Select("SELECT * FROM case_car ORDER BY rand() LIMIT 3;")
    public List<Case> findCaseByRandom();
}
