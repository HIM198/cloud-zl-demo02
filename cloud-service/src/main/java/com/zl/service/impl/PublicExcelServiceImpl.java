package com.zl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.entity.PublicExcel;
import com.zl.mapper.PublicExcelMapper;
import com.zl.mapper.TeacherMapper;
import com.zl.service.PublicExcelService;
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
public class PublicExcelServiceImpl extends ServiceImpl<PublicExcelMapper, PublicExcel> implements PublicExcelService {
    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public IPage<PublicExcel> findPage(SearchVo searchVo) {
        Page<PublicExcel> page = new Page<>(searchVo.getPageNumber(),searchVo.getPageCount());

        QueryWrapper<PublicExcel> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(searchVo.getValueOne()),"file_name",searchVo.getValueOne())
                .eq(StringUtils.isNotEmpty(searchVo.getValueTwo()),"type",searchVo.getValueTwo())
                .eq(StringUtils.isNotEmpty(searchVo.getValueThree()),"tid",searchVo.getValueThree());
        Page<PublicExcel> publicExcelPage = baseMapper.selectPage(page, wrapper);
        for (PublicExcel thisPublicExcel : publicExcelPage.getRecords()) {
            thisPublicExcel.setTeacher(teacherMapper.findTeacherByUid(thisPublicExcel.getTid().toString()));
        }
        return publicExcelPage;
    }
}
