package com.zl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.entity.Comment;
import com.zl.mapper.CommentMapper;
import com.zl.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-25
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
