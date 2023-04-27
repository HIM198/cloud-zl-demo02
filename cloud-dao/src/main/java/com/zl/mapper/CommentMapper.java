package com.zl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-25
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    @Select("select * from comment where cid=#{cid} order by create_time desc")
    public List<Comment> findByCid(String cid);
}
