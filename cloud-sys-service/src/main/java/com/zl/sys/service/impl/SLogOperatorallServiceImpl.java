package com.zl.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zl.sys.entity.SLogOperatorall;
import com.zl.sys.mapper.SLogOperatorallMapper;
import com.zl.sys.service.SLogOperatorallService;
import com.zl.vo.LogVo;
import com.zl.vo.SearchVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-26
 */
@Service
public class SLogOperatorallServiceImpl extends ServiceImpl<SLogOperatorallMapper, SLogOperatorall> implements SLogOperatorallService {

    @Override
    public IPage<SLogOperatorall> findPage(SearchVo searchVo) {
        Page<SLogOperatorall> page = new Page<>(searchVo.getPageNumber(),searchVo.getPageCount());
        QueryWrapper<SLogOperatorall> wrapper = new QueryWrapper<>();
        wrapper
                .orderByDesc("create_date")
                .like(StringUtils.isNotEmpty(searchVo.getValueOne()),"uri",searchVo.getValueOne())
                .eq(StringUtils.isNotEmpty(searchVo.getValueTwo()),"request_id",searchVo.getValueTwo())
                .eq(StringUtils.isNotEmpty(searchVo.getValueThree()),"bean_method",searchVo.getValueThree())
                .like(StringUtils.isNotEmpty(searchVo.getValueFour()),"bean_name",searchVo.getValueFour());
        return baseMapper.selectPage(page,wrapper);
    }

    @Override
    public List<LogVo> downloadLogsData(SearchVo searchVo) {
        ArrayList<LogVo> list = new ArrayList<>();
        QueryWrapper<SLogOperatorall> wrapper = new QueryWrapper<>();
        wrapper
                .orderByDesc("create_date")
                .like(StringUtils.isNotEmpty(searchVo.getValueOne()),"uri",searchVo.getValueOne())
                .eq(StringUtils.isNotEmpty(searchVo.getValueTwo()),"request_id",searchVo.getValueTwo())
                .eq(StringUtils.isNotEmpty(searchVo.getValueThree()),"bean_method",searchVo.getValueThree())
                .like(StringUtils.isNotEmpty(searchVo.getValueFour()),"bean_name",searchVo.getValueFour());
        List<SLogOperatorall> logsList = baseMapper.selectList(wrapper);
        for (SLogOperatorall l : logsList) {
            LogVo logVo = new LogVo(l.getId(),l.getUri(),l.getMethod(),l.getParams(),l.getCreateDate(),l.getBeanName(),l.getBeanMethod(),l.getBeginTime(),l.getEndTime(),l.getRequestTime()+"",l.getResult(),l.getUrl(),l.getOsInfo(),l.getBrowserInfo());
            list.add(logVo);
        }
        return list;
    }
}
