package com.zl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.entity.Achievement;
import com.zl.entity.Case;
import com.zl.mapper.AchievementMapper;
import com.zl.service.AchievementService;
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
public class AchievementServiceImpl extends ServiceImpl<AchievementMapper, Achievement> implements AchievementService {
    @Resource
    private StudentService studentService;
    @Resource
    private TeacherService teacherService;

    @Override
    public IPage<Achievement> findPage(SearchVo searchVo) {
        Page<Achievement> page = new Page<>(searchVo.getPageNumber(),searchVo.getPageCount());

        QueryWrapper<Achievement> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(searchVo.getValueOne()),"subject",searchVo.getValueOne())
                .eq(StringUtils.isNotEmpty(searchVo.getValueTwo()),"tid",searchVo.getValueTwo())
                .eq(StringUtils.isNotEmpty(searchVo.getValueThree()),"sid",searchVo.getValueThree());

        Page<Achievement> achievementPage = baseMapper.selectPage(page, wrapper);
        for (Achievement thisAchievement : achievementPage.getRecords()) {
            thisAchievement.setStudent(studentService.getById(thisAchievement.getSid()));
            thisAchievement.setTeacher(teacherService.getById(thisAchievement.getTid()));
        }
        return achievementPage;
    }
}
