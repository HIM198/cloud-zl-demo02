package com.zl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.entity.Case;
import com.zl.entity.Programme;
import com.zl.vo.SearchVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-25
 */
public interface CaseService extends IService<Case> {

    IPage<Case> findPage(SearchVo searchVo);
}
