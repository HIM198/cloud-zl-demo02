package com.zl.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zl.entity.*;
import com.zl.service.CustomerService;
import com.zl.service.UserService;
import com.zl.sys.entity.Role;
import com.zl.sys.entity.UserRole;
import com.zl.sys.service.UserRoleService;
import com.zl.vo.Result;
import com.zl.vo.ResultMessage;
import com.zl.vo.SearchVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-25
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Resource
    private CustomerService customerService;
    @Resource
    private UserService userService;
    @Resource
    private UserRoleService userRoleService;

    //列表查询
    @PostMapping("/findPage")
    public IPage<Customer> findPage(@RequestBody SearchVo searchVo){
        return customerService.findPage(searchVo);
    }

    //添加客户
    @PostMapping("/save")
    public Result saveTown(@RequestBody Customer customer){
        User user = new User();
        user.setName(customer.getUsername());
        user.setPassword(customer.getPassword());
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
        userRole.setRoleId(3);
        userRole.setCreateTime(new Date());
        userRoleService.save(userRole);

        customer.setUid(user.getId());

        return customerService.save(customer)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    //通过id查询
    @GetMapping("/findById/{id}")
    public Customer findById(@PathVariable("id") String id){
        return customerService.getById(id);
    }

    //修改
    @PostMapping("/update")
    public Result update(@RequestBody Customer customer){
        return customerService.updateById(customer)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    @GetMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") String id){
        return customerService.removeById(id)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

}

