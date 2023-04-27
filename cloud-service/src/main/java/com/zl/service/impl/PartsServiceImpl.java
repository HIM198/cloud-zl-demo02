package com.zl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.entity.Parts;
import com.zl.entity.PartsCate;
import com.zl.mapper.PartsMapper;
import com.zl.service.PartsCateService;
import com.zl.service.PartsService;
import com.zl.vo.SearchVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-25
 */
@Service
public class PartsServiceImpl extends ServiceImpl<PartsMapper, Parts> implements PartsService {
    @Resource
    private PartsCateService partsCateService;

    @Override
    public IPage<Parts> findPage(SearchVo searchVo) {
        Page<Parts> page = new Page<>(searchVo.getPageNumber(),searchVo.getPageCount());

        QueryWrapper<Parts> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(searchVo.getValueOne()),"name",searchVo.getValueOne());
        Page<Parts> selectPage = baseMapper.selectPage(page, wrapper);
        List<Parts> list = selectPage.getRecords();
        for (Parts thisParts : list) {
            thisParts.setPartsCate(partsCateService.getById(thisParts.getCid()));
        }
        return selectPage;
    }

    @Override
    public String checkData(Parts parts) {
        return null;
    }
}
