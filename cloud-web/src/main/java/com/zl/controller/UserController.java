package com.zl.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.code.kaptcha.Constants;
import com.zl.entity.User;
import com.zl.service.UserService;
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
 * @since 2022-10-25
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private JwtConfig jwtConfig;


    @GetMapping("/findAll")
    public List<User> findAll(){
        return userService.list();
    }

    //登录接口
    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpServletRequest request){
        String sessionCode = (String)request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (!sessionCode.equals(user.getCode())){
            return Result.error().message("验证码有误");
        }
        return userService.login(user);
    }

    //新增用户
    @PostMapping("/saveUser")
    public Result saveUser(@RequestBody User user){
        //密码加密
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        String message = userService.checkData(user);
        if (StringUtils.isNotEmpty(message)){
            return Result.error().message(message);
        }
        user.setStatus("ACTIVE");
        return userService.save(user)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    /**
     *
     * @author ZHD-xinwei.Fan
     * @creed: 用户列表查询
     * @date 2022/10/27 15:59
     */
    @PostMapping("/findPage")
    public IPage<User> findPage(@RequestBody SearchVo searchVo){
        return userService.findPage(searchVo);
    }

    //通过id查询
    @GetMapping("/findById/{id}")
    public User findById(@PathVariable("id") String id){
        return userService.getById(id);
    }

    /**
    *
    * @return com.zl.vo.Result
    * @author ZHD-xinwei.Fan
    * @creed: 编辑用户信息...
    * @date 2022/10/29 15:16
     * */
    @PostMapping("/update")
    public Result update(@RequestBody User user){
        //密码加密
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        return userService.updateById(user)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    /**
    *
    * @return com.zl.vo.Result
    * @author ZHD-xinwei.Fan
    * @creed: 删除用户...
    * @date 2022/10/29 15:34
    */
    @GetMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") String id){
        return userService.deleteUser(id)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    /**
    *
    * @return com.zl.vo.Result
    * @author ZHD-xinwei.Fan
    * @creed: 修改密码...
    * @date 2022/11/27 15:25
    */
    @GetMapping("/updatePassword/{password}")
    public Result updatePassword(@PathVariable("password") String password,HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        Claims claim = jwtConfig.getTokenClaim(authorization);
        Integer uid = Integer.parseInt(claim.getSubject());
        User dbUser = userService.getById(uid);
        dbUser.setPassword(DigestUtils.md5Hex(password));
        return userService.updateById(dbUser)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }


}

