package com.zl.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zl.entity.Homework;
import com.zl.entity.PublicExcel;
import com.zl.entity.Student;
import com.zl.entity.Teacher;
import com.zl.mapper.StudentMapper;
import com.zl.service.HomeworkService;
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
@RequestMapping("/homework")
@ApiModel("学生作业表")
public class HomeworkController {

    @Resource
    private JwtConfig jwtConfig;
    @Resource
    private HomeworkService homeworkService;
    @Resource
    private StudentMapper studentMapper;

    //列表查询
    @PostMapping("/findPage")
    public IPage<Homework> findPage(@RequestBody SearchVo searchVo, HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        Claims claim = jwtConfig.getTokenClaim(authorization);
        Integer uid = Integer.parseInt(claim.getSubject());
        searchVo.setValueTwo(uid.toString());
        return homeworkService.findPage(searchVo);
    }

    //列表查询
    @PostMapping("/findPageAll")
    public IPage<Homework> findPageAll(@RequestBody SearchVo searchVo){
        return homeworkService.findPage(searchVo);
    }

    //添加
    @PostMapping("/save")
    public Result saveTown(@RequestBody Homework homework, HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        Claims claim = jwtConfig.getTokenClaim(authorization);
        Integer uid = Integer.parseInt(claim.getSubject());
        Student dbStudent = studentMapper.findStudentByUid(uid.toString());
        homework.setSid(dbStudent.getId());

        return homeworkService.save(homework)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    //通过id查询
    @GetMapping("/findById/{id}")
    public Homework findById(@PathVariable("id") String id){
        return homeworkService.getById(id);
    }

    //修改
    @PostMapping("/update")
    public Result update(@RequestBody Homework homework){
        return homeworkService.updateById(homework)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    @GetMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") String id){
        return homeworkService.removeById(id)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }
}

