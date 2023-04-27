package com.zl.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zl.sys.entity.Role;
import com.zl.sys.service.RoleService;
import com.zl.vo.Result;
import com.zl.vo.ResultMessage;
import com.zl.vo.SearchVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-27
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
    * @author ZHD-xinwei.Fan
    * @creed: 新增角色
    * @date 2022/10/27 15:36
    */
    @PostMapping("/saveRole")
    public Result saveRole(@RequestBody Role role){
        String message = roleService.checkData(role);
        if (StringUtils.isNotEmpty(message)){
            return Result.error().message(message);
        }
        role.setStatus("ACTIVE");
        return roleService.save(role)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    /**
    * @author ZHD-xinwei.Fan
    * @creed: 角色分页列表查询
    * @date 2022/10/27 15:59
    */
    @PostMapping("/findPage")
    public IPage<Role> findPage(@RequestBody SearchVo searchVo){
        return roleService.findPage(searchVo);
    }

    /**
    *
    * @return
    * @author ZHD-xinwei.Fan
    * @creed: 查询所有角色...
    * @date 2022/10/28 11:07
    */
    @GetMapping("/findAll")
    public List<Role> findAll(){
        return roleService.findAll();
    }

    /**
    *
    * @return com.zl.vo.Result
    * @author ZHD-xinwei.Fan
    * @creed: 删除角色...
    * @date 2022/10/29 15:46
    */
    @GetMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") String id){
        return roleService.deleteRole(id)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    /**
    *
    * @return com.zl.sys.entity.Role
    * @author ZHD-xinwei.Fan
    * @creed: 通过id查询角色...
    * @date 2022/10/29 15:49
    */
    @GetMapping("/findById/{id}")
    public Role findById(@PathVariable("id") String id){
        return roleService.getById(id);
    }

    /**
    *
    * @return com.zl.vo.Result
    * @author ZHD-xinwei.Fan
    * @creed: 修改角色信息...
    * @date 2022/10/29 15:49
    */
    @PostMapping("/updateRole")
    public Result update(@RequestBody Role role){
        return roleService.updateById(role)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }
}

