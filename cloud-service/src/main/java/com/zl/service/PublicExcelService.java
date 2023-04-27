package com.zl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.entity.PublicExcel;
import com.zl.vo.SearchVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-30
 */
public interface PublicExcelService extends IService<PublicExcel> {

    IPage<PublicExcel> findPage(SearchVo searchVo);
}
