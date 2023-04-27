package com.zl.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zl.entity.Student;
import com.zl.entity.Teacher;
import com.zl.entity.User;
import com.zl.service.StudentService;
import com.zl.service.UserService;
import com.zl.sys.entity.UserRole;
import com.zl.sys.service.UserRoleService;
import com.zl.vo.Result;
import com.zl.vo.ResultMessage;
import com.zl.vo.SearchVo;
import io.swagger.annotations.ApiModel;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-30
 */
@RestController
@RequestMapping("/student")
@ApiModel("学生表")
public class StudentController {
    @Resource
    private StudentService studentService;
    @Resource
    private UserService userService;
    @Resource
    private UserRoleService userRoleService;
    
    //列表查询
    @PostMapping("/findPage")
    public IPage<Student> findPage(@RequestBody SearchVo searchVo){
        return studentService.findPage(searchVo);
    }

    //添加
    @PostMapping("/save")
    public Result saveTown(@RequestBody Student student){
        User user = new User();
        user.setName(student.getUsername());
        user.setPassword(student.getPassword());
        //密码加密
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));

        String message = userService.checkData(user);
        if (StringUtils.isNotEmpty(message)){
            return Result.error().message(message);
        }
        user.setStatus("ACTIVE");
        userService.save(user);

        UserRole userRole = new UserRole();
        userRole.setUid(user.getId());
        userRole.setRoleId(5);
        userRole.setCreateTime(new Date());
        userRoleService.save(userRole);

        student.setUid(user.getId());
        return studentService.save(student)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    //通过id查询
    @GetMapping("/findById/{id}")
    public Student findById(@PathVariable("id") String id){
        return studentService.getById(id);
    }

    //修改
    @PostMapping("/update")
    public Result update(@RequestBody Student student){
        return studentService.updateById(student)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    @GetMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") String id){
        return studentService.removeById(id)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    @GetMapping("/all")
    public List<Student> findAll(){
        return studentService.list();
    }

}

