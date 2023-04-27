package com.zl.service.impl;

import com.zl.entity.OrderItem;
import com.zl.mapper.OrderItemMapper;
import com.zl.service.OrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-25
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
