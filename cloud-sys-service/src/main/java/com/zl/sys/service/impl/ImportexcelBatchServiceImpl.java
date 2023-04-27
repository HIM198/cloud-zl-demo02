package com.zl.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.sys.entity.ImportexcelBatch;
import com.zl.sys.entity.Role;
import com.zl.sys.mapper.ImportexcelBatchMapper;
import com.zl.sys.service.ImportexcelBatchService;
import com.zl.vo.SearchVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-31
 */
@Service
public class ImportexcelBatchServiceImpl extends ServiceImpl<ImportexcelBatchMapper, ImportexcelBatch> implements ImportexcelBatchService {

    @Override
    public IPage<ImportexcelBatch> findPage(SearchVo searchVo) {
        Page<ImportexcelBatch> page = new Page<>(searchVo.getPageNumber(),searchVo.getPageCount());

        QueryWrapper<ImportexcelBatch> wrapper = new QueryWrapper<>();
        wrapper.eq("tid","40287c5d748a904a01748b716a630101")
                .orderByDesc("create_time");

        return baseMapper.selectPage(page,wrapper);
    }
}
