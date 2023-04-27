package com.zl.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.sys.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-27
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    @Select("select pid from role_permission where rid=#{rid}")
    public List<String> pidList(Integer rid);

    //查询当前角色下的权限菜单
    @Select("select pid from role_permission  r \n" +
            "INNER JOIN permission p on r.pid=p.id where r.rid=#{rid} and p.parent_id!='0';")
    public List<String> pidListAndNotParentId(Integer rid);

}
