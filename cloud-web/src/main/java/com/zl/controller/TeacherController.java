package com.zl.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zl.entity.Parts;
import com.zl.entity.Teacher;
import com.zl.entity.User;
import com.zl.service.TeacherService;
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
@RequestMapping("/teacher")
@ApiModel("教师表")
public class TeacherController {
    @Resource
    private TeacherService teacherService;
    @Resource
    private UserService userService;
    @Resource
    private UserRoleService userRoleService;
    
    //列表查询
    @PostMapping("/findPage")
    public IPage<Teacher> findPage(@RequestBody SearchVo searchVo){
        return teacherService.findPage(searchVo);
    }

    //添加
    @PostMapping("/save")
    public Result saveTown(@RequestBody Teacher teacher){
        User user = new User();
        user.setName(teacher.getUsername());
        user.setPassword(teacher.getPassword());
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
        userRole.setRoleId(4);
        userRole.setCreateTime(new Date());
        userRoleService.save(userRole);

        teacher.setUid(user.getId());
        return teacherService.save(teacher)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    //通过id查询
    @GetMapping("/findById/{id}")
    public Teacher findById(@PathVariable("id") String id){
        return teacherService.getById(id);
    }

    //修改
    @PostMapping("/update")
    public Result update(@RequestBody Teacher teacher){
        return teacherService.updateById(teacher)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    @GetMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") String id){
        return teacherService.removeById(id)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    @GetMapping("/all")
    public List<Teacher> findAll(){
        return teacherService.list();
    }
}

