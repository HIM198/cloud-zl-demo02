package com.zl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.entity.User;
import com.zl.mapper.UserMapper;
import com.zl.service.UserService;
import com.zl.sys.controller.config.jwt.JwtConfig;
import com.zl.sys.entity.Role;
import com.zl.sys.entity.UserRole;
import com.zl.sys.mapper.UserRoleMapper;
import com.zl.sys.service.RoleService;
import com.zl.vo.Result;
import com.zl.vo.SearchVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Resource
    private JwtConfig jwtConfig;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RoleService roleService;


    @Override
    public Result login(User user) {
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        List<User> list = baseMapper.selectList(null);
        for (User thisUser : list) {
            //查询数据库有没有当前登录账号
            if (thisUser.getName().equals(user.getName()) && thisUser.getPassword().equals(user.getPassword())){
                //查询该账号是否存在权限
                Integer rid = userRoleMapper.findRidByUid(thisUser.getId());
                if (rid==null){
                    return Result.error().message("改账号未开通权限!");
                }
                //判断账号是否禁用
                Role role = roleService.getById(rid);
                if (!thisUser.getStatus().equals("ACTIVE")){
                    return Result.error().message("您的账号已被禁用!");
                }
                //生成jwt给前端
                String token = jwtConfig.createToken(thisUser.getId().toString());
                return Result.ok().data("token",token).data("username",thisUser.getName()).data("role",role.getName());
            }
        }
        return Result.error().message("用户名或密码有误!");
    }

    @Override
    public String checkData(User user) {
        User dbUser = baseMapper.findUserByName(user.getName());
        if (dbUser!=null){
            return "当前账号已存在！";
        }
        return null;
    }

    @Override
    public IPage<User> findPage(SearchVo searchVo) {
        Page<User> page = new Page<>(searchVo.getPageNumber(),searchVo.getPageCount());

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(searchVo.getValueOne())){
            wrapper.like("name",searchVo.getValueOne());
        }

        return baseMapper.selectPage(page,wrapper);
    }

    @Override
    public boolean deleteUser(String id) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<UserRole>();
        wrapper.eq("uid",id);
        //删除改用户的权限
        userRoleMapper.delete(wrapper);
        //删除用户账号
        return baseMapper.deleteById(id)==1;
    }
}
