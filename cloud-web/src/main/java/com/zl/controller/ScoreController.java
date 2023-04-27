package com.zl.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zl.entity.*;
import com.zl.mapper.StudentMapper;
import com.zl.mapper.TeacherMapper;
import com.zl.service.ScoreService;
import com.zl.service.StudentService;
import com.zl.service.TeacherService;
import com.zl.sys.controller.config.jwt.JwtConfig;
import com.zl.sys.entity.UserRole;
import com.zl.vo.Result;
import com.zl.vo.ResultMessage;
import com.zl.vo.SearchVo;
import io.jsonwebtoken.Claims;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XW.Fan
 * @since 2023-02-03
 */
@RestController
@RequestMapping("/score")
public class ScoreController {
    @Resource
    private ScoreService scoreService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private StudentService studentService;
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private JwtConfig jwtConfig;

    //列表查询
    @PostMapping("/findPage")
    public IPage<Score> findPage(@RequestBody SearchVo searchVo){
        return scoreService.findPage(searchVo);
    }


    //列表查询
    @PostMapping("/findPageByStu")
    public IPage<Score> findPageByStu(@RequestBody SearchVo searchVo, HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        Claims claim = jwtConfig.getTokenClaim(authorization);
        Integer uid = Integer.parseInt(claim.getSubject());
        Student student = studentMapper.findStudentByUid(uid.toString());
        searchVo.setValueThree(student.getId().toString());

        System.out.println(uid);
        return scoreService.findPage(searchVo);
    }

    //添加
    @PostMapping("/save")
    public Result saveTown(@RequestBody Score score, HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        Claims claim = jwtConfig.getTokenClaim(authorization);
        Integer tid = Integer.parseInt(claim.getSubject());
        Teacher teacher = teacherMapper.findTeacherByUid(tid+"");
        score.setTname(teacher.getName());
        score.setTid(teacher.getId());
        Student dbStudent = studentService.getById(score.getSid());
        score.setSname(dbStudent.getName());
        return scoreService.save(score)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    //通过id查询
    @GetMapping("/findById/{id}")
    public Score findById(@PathVariable("id") String id){
        return scoreService.getById(id);
    }

    //修改
    @PostMapping("/update")
    public Result update(@RequestBody  Score score){
        return scoreService.updateById(score)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    @GetMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") String id){
        return scoreService.removeById(id)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    @GetMapping("/all")
    public List<Score> findAll(){
        return scoreService.list();
    }
}

