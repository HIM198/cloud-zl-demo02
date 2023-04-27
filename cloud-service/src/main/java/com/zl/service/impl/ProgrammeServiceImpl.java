package com.zl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.entity.Parts;
import com.zl.entity.PartsCate;
import com.zl.entity.Programme;
import com.zl.mapper.ProgrammeMapper;
import com.zl.service.ProgrammeService;
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
public class ProgrammeServiceImpl extends ServiceImpl<ProgrammeMapper, Programme> implements ProgrammeService {

    @Override
    public IPage<Programme> findPage(SearchVo searchVo) {
        Page<Programme> page = new Page<>(searchVo.getPageNumber(),searchVo.getPageCount());

        QueryWrapper<Programme> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(searchVo.getValueOne()),"name",searchVo.getValueOne());

        return baseMapper.selectPage(page,wrapper);
    }
}
