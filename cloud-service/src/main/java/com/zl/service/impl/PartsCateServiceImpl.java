package com.zl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.entity.Customer;
import com.zl.entity.PartsCate;
import com.zl.mapper.PartsCateMapper;
import com.zl.service.PartsCateService;
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
public class PartsCateServiceImpl extends ServiceImpl<PartsCateMapper, PartsCate> implements PartsCateService {

    @Override
    public IPage<PartsCate> findPage(SearchVo searchVo) {
        Page<PartsCate> page = new Page<>(searchVo.getPageNumber(),searchVo.getPageCount());

        QueryWrapper<PartsCate> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(searchVo.getValueOne()),"parts_name",searchVo.getValueOne());

        return baseMapper.selectPage(page,wrapper);
    }

    @Override
    public String checkData(PartsCate partsCate) {
        PartsCate dbPart = baseMapper.findPartCateByName(partsCate.getPartsName());
        if (dbPart!=null){
            return "该配件分类已存在";
        }
        return null;
    }
}
