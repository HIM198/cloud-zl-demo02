package com.zl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.entity.Evaluate;
import com.zl.vo.SearchVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-30
 */
public interface EvaluateService extends IService<Evaluate> {

    IPage<Evaluate> findPage(SearchVo searchVo);
    IPage<Evaluate> findPageStudent(SearchVo searchVo);

    IPage<Evaluate> findPageAdmin(SearchVo searchVo);
}
