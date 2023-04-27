package com.zl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.entity.PartsCate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-25
 */
@Mapper
public interface PartsCateMapper extends BaseMapper<PartsCate> {
    @Select("select * from parts_cate where parts_name=#{name}")
    public PartsCate findPartCateByName(String name);
}
