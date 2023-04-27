package com.zl.sys.controller;


import com.zl.sys.service.RolePermissionService;
import com.zl.vo.Result;
import com.zl.vo.ResultMessage;
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
@RequestMapping("/role-permission")
public class RolePermissionController {
    @Resource
    private RolePermissionService permissionService;

    /**
    *
    * @return
    * @author ZHD-xinwei.Fan
    * @creed: 保存角色菜单...
    * @date 2022/10/28 15:20
    */
    @GetMapping("/saveRolePermission/{rid}")
    public Result saveRolePermission(@PathVariable("rid") Integer rid, String pids){
        return permissionService.saveRolePermission(rid,pids)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    /**
    *
    * @return java.util.List<java.lang.String>
    * @author ZHD-xinwei.Fan
    * @creed: 查询该角色当前的菜单...
    * @date 2022/10/28 15:43
    */
    @GetMapping("/findMenuIdByRid/{rid}")
    public List<String> findMenuIdByRid(@PathVariable("rid") Integer rid){
        return permissionService.findMenuNotHaveParentId(rid);
    }

}

