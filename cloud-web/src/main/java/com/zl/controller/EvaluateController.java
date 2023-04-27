package com.zl.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zl.entity.Evaluate;
import com.zl.entity.Homework;
import com.zl.entity.Student;
import com.zl.mapper.StudentMapper;
import com.zl.service.EvaluateService;
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
@RequestMapping("/evaluate")
@ApiModel("评价表")
public class EvaluateController {
    @Resource
    private EvaluateService evaluateService;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private JwtConfig jwtConfig;

    //列表查询
    @PostMapping("/findPage")
    public IPage<Evaluate> findPage(@RequestBody SearchVo searchVo, HttpServletRequest request){
        return evaluateService.findPage(searchVo);
    }


    //列表查询
    @PostMapping("/findPageStudent")
    public IPage<Evaluate> findPageStudent(@RequestBody SearchVo searchVo, HttpServletRequest request){
        return evaluateService.findPageStudent(searchVo);
    }

    //列表查询
    @PostMapping("/findPageAdmin")
    public IPage<Evaluate> findPageAdmin(@RequestBody SearchVo searchVo, HttpServletRequest request){
        return evaluateService.findPageAdmin(searchVo);
    }

    //添加
    @PostMapping("/save")
    public Result saveTown(@RequestBody Evaluate evaluate, HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        Claims claim = jwtConfig.getTokenClaim(authorization);
        Integer uid = Integer.parseInt(claim.getSubject());
        Student dbStudent = studentMapper.findStudentByUid(uid.toString());
        evaluate.setSid(dbStudent.getId());

        return evaluateService.save(evaluate)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    //添加
    @PostMapping("/saveAdmin")
    public Result saveAdmin(@RequestBody Evaluate evaluate, HttpServletRequest request){
        return evaluateService.save(evaluate)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    //通过id查询
    @GetMapping("/findById/{id}")
    public Evaluate findById(@PathVariable("id") String id){
        return evaluateService.getById(id);
    }

    //修改
    @PostMapping("/update")
    public Result update(@RequestBody Evaluate evaluate){
        return evaluateService.updateById(evaluate)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    @GetMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") String id){
        return evaluateService.removeById(id)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }
}

