package com.zl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.entity.PartsCate;
import com.zl.entity.Score;
import com.zl.mapper.ScoreMapper;
import com.zl.service.ScoreService;
import com.zl.vo.SearchVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XW.Fan
 * @since 2023-02-03
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {

    @Override
    public IPage<Score> findPage(SearchVo searchVo) {
        Page<Score> page = new Page<>(searchVo.getPageNumber(),searchVo.getPageCount());

        QueryWrapper<Score> wrapper = new QueryWrapper<>();
        wrapper
                .like(StringUtils.isNotEmpty(searchVo.getValueOne()),"course",searchVo.getValueOne())
                .like(StringUtils.isNotEmpty(searchVo.getValueThree()),"sid",searchVo.getValueThree());

        return baseMapper.selectPage(page,wrapper);
    }
}
