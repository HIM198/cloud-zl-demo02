package com.zl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.entity.Case;
import com.zl.entity.Curriculum;
import com.zl.mapper.CurriculumMapper;
import com.zl.service.CurriculumService;
import com.zl.service.TeacherService;
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
 * @since 2022-11-30
 */
@Service
public class CurriculumServiceImpl extends ServiceImpl<CurriculumMapper, Curriculum> implements CurriculumService {
    @Resource
    private TeacherService teacherService;

    @Override
    public IPage<Curriculum> findPage(SearchVo searchVo) {
        Page<Curriculum> page = new Page<>(searchVo.getPageNumber(),searchVo.getPageCount());

        QueryWrapper<Curriculum> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(searchVo.getValueOne()),"name",searchVo.getValueOne());
        Page<Curriculum> curriculumPage = baseMapper.selectPage(page, wrapper);
        for (Curriculum thisCurriculum : curriculumPage.getRecords()) {
            thisCurriculum.setTeacher(teacherService.getById(thisCurriculum.getTid()));
        }
        return curriculumPage;
    }
}
