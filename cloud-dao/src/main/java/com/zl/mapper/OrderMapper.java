package com.zl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-25
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
