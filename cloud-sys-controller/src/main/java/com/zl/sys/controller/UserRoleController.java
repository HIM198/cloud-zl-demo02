package com.zl.sys.controller;


import com.zl.entity.User;
import com.zl.sys.controller.config.jwt.JwtConfig;
import com.zl.sys.entity.Permission;
import com.zl.sys.entity.UserRole;
import com.zl.sys.service.UserRoleService;
import com.zl.vo.Result;
import com.zl.vo.ResultMessage;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/user-role")
public class UserRoleController {
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private JwtConfig jwtConfig;


    /**
    *
    * @author ZHD-xinwei.Fan
    * @creed: 分配角色...
    * @date 2022/10/28 11:18
    */
    @GetMapping("/assignUserRole/{uid}/{rid}")
    public Result assignUserRole(@PathVariable("uid") Integer uid,@PathVariable("rid") Integer rid){
        return userRoleService.assignUserRole(uid,rid)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    /**
    *
    * @return com.zl.sys.entity.UserRole
    * @author ZHD-xinwei.Fan
    * @creed: 查询用户角色...
    * @date 2022/10/29 10:45
    */
    @GetMapping("/findUserRole/{uid}")
    public UserRole findUserRole(@PathVariable("uid") Integer uid){
        return userRoleService.findUserRoleByUid(uid);
    }

    /**
    *
    * @return java.util.List<com.zl.sys.entity.Permission>
    * @author ZHD-xinwei.Fan
    * @creed: 查询当前登录用户的菜单...
    * @date 2022/10/29 10:45
    */
    @GetMapping("/findMenus")
    public List<Permission> findMenus(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        Claims claim = jwtConfig.getTokenClaim(authorization);
        Integer uid = Integer.parseInt(claim.getSubject());
        return userRoleService.findPermission(uid);
    }


    /**
    *
    * @return java.util.List<com.zl.entity.User>
    * @author ZHD-xinwei.Fan
    * @creed: 通过角色id查询用户...
    * @date 2022/10/29 16:03
    */
    @GetMapping("/findUserListByRid/{id}")
    public List<User> findUserListByRid(@PathVariable("id") String id){
        return userRoleService.findUserByRid(id);
    }
}

