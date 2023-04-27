package com.zl.sys.controller;


import com.zl.sys.entity.Permission;
import com.zl.sys.service.PermissionService;
import com.zl.vo.Result;
import com.zl.vo.ResultMessage;
import com.zl.vo.SearchVo;
import com.zl.vo.TreeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.nio.channels.Pipe;
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
@RequestMapping("/permission")
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    /**
    *
    * @author ZHD-xinwei.Fan
    * @creed: 新增菜单...
    * @date 2022/10/28 14:05
    */
    @PostMapping("/saveMenu")
    public Result saveMenu(@RequestBody Permission permission){
        String message = permissionService.checkData(permission);
        if (StringUtils.isNotEmpty(message)){
            return Result.error().message(message);
        }
        permission.setStatus("ACTIVE");
        String icon = "fa fa-"+permission.getIcon();
        permission.setIcon(icon);
        return permissionService.save(permission)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    /**
    *
    * @author ZHD-xinwei.Fan
    * @creed: 查询菜单列表...
    * @date 2022/10/28 14:41
    */
    @PostMapping("/findAll")
    public List<Permission> findAll(@RequestBody SearchVo searchVo){
        return permissionService.findAll(searchVo);
    }

    /**
    *
    * @return java.util.List<com.zl.vo.TreeVo>
    * @author ZHD-xinwei.Fan
    * @creed: 查询角色列表中的菜单分配列表...
    * @date 2022/10/28 15:52
    */
    @GetMapping("/findTree")
    public List<TreeVo> findTree(){
        return permissionService.findTree();
    }


    /**
    *
    * @return java.util.List<java.lang.String>
    * @author ZHD-xinwei.Fan
    * @creed: 查询所有父菜单id...
    * @date 2022/10/28 15:54
    */
    @GetMapping("/findAllId")
    public List<String> findAllId(){
        return permissionService.findIdList();
    }

    /**
    *
    * @return com.zl.sys.entity.Permission
    * @author ZHD-xinwei.Fan
    * @creed: 通过id查询菜单详情...
    * @date 2022/10/29 11:46
    */
    @GetMapping("/findById/{id}")
    public Permission findById(@PathVariable("id") String id){
        return permissionService.getById(id);
    }

    /**
    *
    * @return com.zl.vo.Result
    * @author ZHD-xinwei.Fan
    * @creed: 修改菜单信息...
    * @date 2022/10/29 11:51
    */
    @PostMapping("/update")
    public Result update(@RequestBody Permission permission){
        if (!permission.getIcon().contains("fa fa-")){
            String icon = "fa fa-"+permission.getIcon();
            permission.setIcon(icon);
        }
        return permissionService.updateById(permission)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }


}

