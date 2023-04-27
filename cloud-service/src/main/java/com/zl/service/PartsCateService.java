package com.zl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.entity.Customer;
import com.zl.entity.PartsCate;
import com.zl.entity.User;
import com.zl.vo.SearchVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-25
 */
public interface PartsCateService extends IService<PartsCate> {
    //列表模糊分页查询
    public IPage<PartsCate> findPage(SearchVo searchVo);
    //校验数据
    public String checkData(PartsCate partsCate);
}
