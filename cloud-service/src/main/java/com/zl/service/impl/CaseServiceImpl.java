package com.zl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.entity.Case;
import com.zl.entity.Customer;
import com.zl.entity.Programme;
import com.zl.mapper.CaseMapper;
import com.zl.service.CaseService;
import com.zl.vo.SearchVo;
import org.apache.commons.lang3.StringUtils;
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
public class CaseServiceImpl extends ServiceImpl<CaseMapper, Case> implements CaseService {

    @Override
    public IPage<Case> findPage(SearchVo searchVo) {
        Page<Case> page = new Page<>(searchVo.getPageNumber(),searchVo.getPageCount());

        QueryWrapper<Case> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(searchVo.getValueOne()),"title",searchVo.getValueOne());

        return baseMapper.selectPage(page,wrapper);
    }
}
