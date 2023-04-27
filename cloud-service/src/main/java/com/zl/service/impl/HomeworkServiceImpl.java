package com.zl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.entity.Homework;
import com.zl.entity.PublicExcel;
import com.zl.mapper.HomeworkMapper;
import com.zl.mapper.StudentMapper;
import com.zl.service.HomeworkService;
import com.zl.service.StudentService;
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
public class HomeworkServiceImpl extends ServiceImpl<HomeworkMapper, Homework> implements HomeworkService {
    @Resource
    private StudentService studentService;

    @Override
    public IPage<Homework> findPage(SearchVo searchVo) {
        Page<Homework> page = new Page<>(searchVo.getPageNumber(),searchVo.getPageCount());

        QueryWrapper<Homework> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(searchVo.getValueOne()),"file_name",searchVo.getValueOne())
                .eq(StringUtils.isNotEmpty(searchVo.getValueTwo()),"sid",searchVo.getValueTwo());
        Page<Homework> publicExcelPage = baseMapper.selectPage(page, wrapper);
        for (Homework thisPublicExcel : publicExcelPage.getRecords()) {
            thisPublicExcel.setStudent(studentService.getById(thisPublicExcel.getSid()));
        }
        return publicExcelPage;
    }
}
