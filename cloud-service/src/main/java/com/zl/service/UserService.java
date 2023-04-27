package com.zl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.entity.User;
import com.zl.sys.entity.Role;
import com.zl.vo.Result;
import com.zl.vo.SearchVo;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-25
 */
public interface UserService extends IService<User> {
    //登录
    public Result login(User user);
    //校验数据
    public String checkData(User user);
    //列表模糊分页查询
    public IPage<User> findPage(SearchVo searchVo);
    //删除用户
    public boolean deleteUser(String id);

}
