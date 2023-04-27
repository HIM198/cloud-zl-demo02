package com.zl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.entity.Student;
import com.zl.entity.Teacher;
import com.zl.mapper.TeacherMapper;
import com.zl.service.TeacherService;
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
 * @since 2022-11-30
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Resource
    private UserService userService;

    @Override
    public IPage<Teacher> findPage(SearchVo searchVo) {
        Page<Teacher> page = new Page<>(searchVo.getPageNumber(),searchVo.getPageCount());

        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(searchVo.getValueOne()),"name",searchVo.getValueOne());
        Page<Teacher> teacherPage = baseMapper.selectPage(page, wrapper);
        for (Teacher thisTeacher : teacherPage.getRecords()) {
            thisTeacher.setUser(userService.getById(thisTeacher.getUid()));
        }
        return teacherPage;
    }
}
