package com.zl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.entity.Evaluate;
import com.zl.entity.Homework;
import com.zl.mapper.EvaluateMapper;
import com.zl.service.EvaluateService;
import com.zl.service.StudentService;
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
public class EvaluateServiceImpl extends ServiceImpl<EvaluateMapper, Evaluate> implements EvaluateService {
    @Resource
    private TeacherService teacherService;
    @Resource
    private StudentService studentService;
    @Override
    public IPage<Evaluate> findPage(SearchVo searchVo) {
        Page<Evaluate> page = new Page<>(searchVo.getPageNumber(),searchVo.getPageCount());
        QueryWrapper<Evaluate> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(searchVo.getValueOne()),"context",searchVo.getValueOne());
        Page<Evaluate> evaluatePage = baseMapper.selectPage(page, wrapper);
        for (Evaluate thisEvaluate : evaluatePage.getRecords()) {
            thisEvaluate.setTeacher(teacherService.getById(thisEvaluate.getTid()));
            if (thisEvaluate.getSid()!=null){
                thisEvaluate.setStudent(studentService.getById(thisEvaluate.getSid()));
            }
        }
        return evaluatePage;
    }

    @Override
    public IPage<Evaluate> findPageStudent(SearchVo searchVo) {
        Page<Evaluate> page = new Page<>(searchVo.getPageNumber(),searchVo.getPageCount());
        QueryWrapper<Evaluate> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(searchVo.getValueOne()),"context",searchVo.getValueOne())
                .isNotNull("sid");
        Page<Evaluate> evaluatePage = baseMapper.selectPage(page, wrapper);

        return evaluatePage;
    }

    @Override
    public IPage<Evaluate> findPageAdmin(SearchVo searchVo) {
        Page<Evaluate> page = new Page<>(searchVo.getPageNumber(),searchVo.getPageCount());
        QueryWrapper<Evaluate> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(searchVo.getValueOne()),"context",searchVo.getValueOne())
                .isNull("sid");
        Page<Evaluate> evaluatePage = baseMapper.selectPage(page, wrapper);

        return evaluatePage;
    }
}
