package com.zl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.entity.Case;
import com.zl.entity.Individualization;
import com.zl.mapper.IndividualizationMapper;
import com.zl.service.IndividualizationService;
import com.zl.service.UserService;
import com.zl.vo.SearchVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-26
 */
@Service
public class IndividualizationServiceImpl extends ServiceImpl<IndividualizationMapper, Individualization> implements IndividualizationService {
    @Resource
    private UserService userService;

    @Override
    public IPage<Individualization> findPage(SearchVo searchVo) {
        Page<Individualization> page = new Page<>(searchVo.getPageNumber(),searchVo.getPageCount());

        QueryWrapper<Individualization> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(searchVo.getValueOne()),"context",searchVo.getValueOne())
                .eq(StringUtils.isNotEmpty(searchVo.getValueTwo()),"uid",searchVo.getValueTwo());
        Page<Individualization> selectPage = baseMapper.selectPage(page, wrapper);
        for (Individualization thisInd : selectPage.getRecords()) {
            thisInd.setUser(userService.getById(thisInd.getUid()));
        }
        return selectPage;
    }
}
