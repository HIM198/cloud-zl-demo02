package com.zl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.entity.Order;
import com.zl.entity.PartsCate;
import com.zl.mapper.OrderMapper;
import com.zl.service.OrderService;
import com.zl.service.UserService;
import com.zl.vo.SearchVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-25
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Resource
    private UserService userService;

    @Override
    public IPage<Order> findPage(SearchVo searchVo) {
        Page<Order> page = new Page<>(searchVo.getPageNumber(),searchVo.getPageCount());

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(searchVo.getValueOne()),"status",searchVo.getValueOne())
                .eq(StringUtils.isNotEmpty(searchVo.getValueTwo()),"uid",searchVo.getValueTwo());
        Page<Order> orderPage = baseMapper.selectPage(page, wrapper);
        for (Order thisOrder : orderPage.getRecords()) {
            thisOrder.setUser(userService.getById(thisOrder.getUid()));
        }
        return orderPage;
    }
}
