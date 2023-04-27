package com.zl.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zl.entity.*;
import com.zl.mapper.StudentMapper;
import com.zl.mapper.TeacherMapper;
import com.zl.service.AchievementService;
import com.zl.service.CurriculumService;
import com.zl.sys.controller.config.jwt.JwtConfig;
import com.zl.vo.Result;
import com.zl.vo.ResultMessage;
import com.zl.vo.SearchVo;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiModel;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-30
 */
@RestController
@RequestMapping("/achievement")
@ApiModel("学生成绩表")
public class AchievementController {
    @Resource
    private AchievementService achievementService;
    @Resource
    private CurriculumService curriculumService;
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private JwtConfig jwtConfig;

    //列表查询
    @PostMapping("/findPage")
    public IPage<Achievement> findPage(@RequestBody SearchVo searchVo, HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        Claims claim = jwtConfig.getTokenClaim(authorization);
        Integer uid = Integer.parseInt(claim.getSubject());
        Teacher teacher = teacherMapper.findTeacherByUid(uid.toString());
        searchVo.setValueTwo(teacher.getId().toString());
        return achievementService.findPage(searchVo);
    }

    //列表查询
    @PostMapping("/findPageByStu")
    public IPage<Achievement> findPageByStu(@RequestBody SearchVo searchVo, HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        Claims claim = jwtConfig.getTokenClaim(authorization);
        Integer uid = Integer.parseInt(claim.getSubject());
        Student student = studentMapper.findStudentByUid(uid.toString());
        searchVo.setValueThree(student.getId().toString());
        return achievementService.findPage(searchVo);
    }

    //列表查询
    @PostMapping("/findPageAdmin")
    public IPage<Achievement> findPageAdmin(@RequestBody SearchVo searchVo, HttpServletRequest request){
        return achievementService.findPage(searchVo);
    }

    //添加
    @PostMapping("/save")
    public Result saveTown(@RequestBody Achievement achievement, HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        Claims claim = jwtConfig.getTokenClaim(authorization);
        Integer uid = Integer.parseInt(claim.getSubject());
        Teacher teacher = teacherMapper.findTeacherByUid(uid.toString());
        achievement.setTid(teacher.getId());

        Curriculum curr = curriculumService.getById(achievement.getCid());
        achievement.setSubject(curr.getName());
        achievement.setStatus("待审核");
        return achievementService.save(achievement)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    //通过id查询
    @GetMapping("/findById/{id}")
    public Achievement findById(@PathVariable("id") String id){
        return achievementService.getById(id);
    }

    //修改
    @PostMapping("/update")
    public Result update(@RequestBody Achievement achievement){
        return achievementService.updateById(achievement)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    @GetMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") String id){
        return achievementService.removeById(id)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    //审核通过
    @GetMapping("/adoption/{id}")
    public Result adoption(@PathVariable("id") String id){
        Achievement achievement = achievementService.getById(id);
        achievement.setStatus("已通过");
        return achievementService.updateById(achievement)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    //审核未通过
    @GetMapping("/rejected/{id}")
    public Result rejected(@PathVariable("id") String id){
        Achievement achievement = achievementService.getById(id);
        achievement.setStatus("未通过");
        return achievementService.updateById(achievement)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

}

