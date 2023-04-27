package com.zl.service.impl;

import com.zl.entity.Emp;
import com.zl.mapper.EmpMapper;
import com.zl.service.EmpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XW.Fan
 * @since 2023-02-02
 */
@Service
public class EmpServiceImpl extends ServiceImpl<EmpMapper, Emp> implements EmpService {

}
