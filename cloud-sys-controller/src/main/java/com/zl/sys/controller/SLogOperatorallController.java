package com.zl.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zl.sys.entity.Role;
import com.zl.sys.entity.SLogOperatorall;
import com.zl.sys.service.SLogOperatorallService;
import com.zl.vo.SearchVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-26
 */
@RestController
@RequestMapping("/s-log-operatorall")
public class SLogOperatorallController {
    @Resource
    private SLogOperatorallService sLogOperatorallService;

    //日志查询
    @PostMapping("/findPage")
    public IPage<SLogOperatorall> findPage(@RequestBody SearchVo searchVo){
        return sLogOperatorallService.findPage(searchVo);
    }

    //id查询
    @GetMapping("/findById/{id}")
    public SLogOperatorall findById(@PathVariable("id") String id){
        return sLogOperatorallService.getById(id);
    }

}

