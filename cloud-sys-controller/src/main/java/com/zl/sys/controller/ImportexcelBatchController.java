package com.zl.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zl.sys.entity.ImportexcelBatch;
import com.zl.sys.service.ImportexcelBatchService;
import com.zl.vo.SearchVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-31
 */
@RestController
@RequestMapping("/importexcel-batch")
public class ImportexcelBatchController {
    @Resource
    private ImportexcelBatchService importexcelBatchService;

    //导入日志查询
    @PostMapping("/findPage")
    public IPage<ImportexcelBatch> findPage(@RequestBody SearchVo searchVo){
        return importexcelBatchService.findPage(searchVo);
    }

}

