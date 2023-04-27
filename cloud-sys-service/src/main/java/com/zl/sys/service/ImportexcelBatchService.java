package com.zl.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.sys.entity.ImportexcelBatch;
import com.zl.vo.SearchVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-31
 */
public interface ImportexcelBatchService extends IService<ImportexcelBatch> {
    public IPage<ImportexcelBatch> findPage(SearchVo searchVo);
}
