package com.zl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.entity.Parts;
import com.zl.vo.SearchVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-25
 */
public interface PartsService extends IService<Parts> {

    IPage<Parts> findPage(SearchVo searchVo);

    String checkData(Parts parts);
}
