package com.zl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.entity.Student;
import com.zl.entity.Teacher;
import com.zl.mapper.StudentMapper;
import com.zl.service.StudentService;
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
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Resource
    private UserService userService;

    @Override
    public IPage<Student> findPage(SearchVo searchVo) {
        Page<Student> page = new Page<>(searchVo.getPageNumber(),searchVo.getPageCount());

        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(searchVo.getValueOne()),"name",searchVo.getValueOne());
        Page<Student> studentPage = baseMapper.selectPage(page, wrapper);
        for (Student thisStudent : studentPage.getRecords()) {
            thisStudent.setUser(userService.getById(thisStudent.getUid()));
        }
        return studentPage;
    }
}
