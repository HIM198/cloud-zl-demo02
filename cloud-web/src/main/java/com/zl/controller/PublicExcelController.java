package com.zl.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zl.entity.Programme;
import com.zl.entity.PublicExcel;
import com.zl.service.PublicExcelService;
import com.zl.sys.controller.config.jwt.JwtConfig;
import com.zl.vo.Result;
import com.zl.vo.ResultMessage;
import com.zl.vo.SearchVo;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiModel;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-30
 */
@RestController
@RequestMapping("/public-excel")
@ApiModel("公共表")
public class PublicExcelController {
    @Resource
    private PublicExcelService publicExcelService;
    @Resource
    private JwtConfig jwtConfig;


    //列表查询
    @PostMapping("/findPage")
    public IPage<PublicExcel> findPage(@RequestBody SearchVo searchVo, HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        Claims claim = jwtConfig.getTokenClaim(authorization);
        Integer uid = Integer.parseInt(claim.getSubject());
        searchVo.setValueThree(uid.toString());
        return publicExcelService.findPage(searchVo);
    }

    //列表查询
    @PostMapping("/findPageByStu")
    public IPage<PublicExcel> findPageByStu(@RequestBody SearchVo searchVo, HttpServletRequest request){
        return publicExcelService.findPage(searchVo);
    }

    //列表查询
    @PostMapping("/findPageAdmin")
    public IPage<PublicExcel> findPageAdmin(@RequestBody SearchVo searchVo){
        return publicExcelService.findPage(searchVo);
    }

    //添加
    @PostMapping("/save")
    public Result saveTown(@RequestBody PublicExcel publicExcel, HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        Claims claim = jwtConfig.getTokenClaim(authorization);
        Integer uid = Integer.parseInt(claim.getSubject());
        publicExcel.setTid(uid);
        System.out.println(publicExcel);
        return publicExcelService.save(publicExcel)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    //通过id查询
    @GetMapping("/findById/{id}")
    public PublicExcel findById(@PathVariable("id") String id){
        return publicExcelService.getById(id);
    }

    //修改
    @PostMapping("/update")
    public Result update(@RequestBody PublicExcel publicExcel){
        return publicExcelService.updateById(publicExcel)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    @GetMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") String id){
        return publicExcelService.removeById(id)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    //审核
    @GetMapping("/examine/{id}/{type}")
    public Result examine(@PathVariable("id") String id,@PathVariable("type") String type){
        PublicExcel publicExcel = publicExcelService.getById(id);
        if (type.equals("通过")){
            publicExcel.setStatus("通过");
        }else{
            publicExcel.setStatus("未通过");
        }
        return publicExcelService.updateById(publicExcel)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }


}

