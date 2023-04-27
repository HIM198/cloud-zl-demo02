package com.zl.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.sys.entity.SLogOperatorall;
import com.zl.vo.LogVo;
import com.zl.vo.SearchVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-26
 */
public interface SLogOperatorallService extends IService<SLogOperatorall> {
    //查询
    public IPage<SLogOperatorall> findPage(SearchVo searchVo);
    //导出
    public List<LogVo> downloadLogsData(SearchVo searchVo);
}
