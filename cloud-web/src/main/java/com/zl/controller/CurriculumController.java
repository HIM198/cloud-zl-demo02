package com.zl.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zl.entity.Case;
import com.zl.entity.Curriculum;
import com.zl.entity.StudentCurr;
import com.zl.entity.Teacher;
import com.zl.mapper.StudentCurrMapper;
import com.zl.mapper.TeacherMapper;
import com.zl.service.CurriculumService;
import com.zl.service.StudentCurrService;
import com.zl.service.TeacherService;
import com.zl.service.UserService;
import com.zl.sys.controller.config.jwt.JwtConfig;
import com.zl.vo.Result;
import com.zl.vo.ResultMessage;
import com.zl.vo.SearchVo;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiModel;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-30
 */
@RestController
@RequestMapping("/curriculum")
@ApiModel("课程表")
public class CurriculumController {
    @Resource
    private CurriculumService curriculumService;
    @Resource
    private StudentCurrService studentCurrService;
    @Resource
    private StudentCurrMapper studentCurrMapper;
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private JwtConfig jwtConfig;

    //列表查询
    @PostMapping("/findPage")
    public IPage<Curriculum> findPage(@RequestBody SearchVo searchVo){
        return curriculumService.findPage(searchVo);
    }

    //添加
    @PostMapping("/save")
    public Result saveTown(@RequestBody Curriculum curriculum){
        boolean save = curriculumService.save(curriculum);
        Integer[] ids = curriculum.getIds();
        for (Integer thisId : ids) {
            StudentCurr studentCurr = new StudentCurr();
            studentCurr.setSid(thisId);
            studentCurr.setCid(curriculum.getId());

            studentCurrService.save(studentCurr);
        }
        return save?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    //通过id查询
    @GetMapping("/findById/{id}")
    public Curriculum findById(@PathVariable("id") String id){
        List<Integer> list = studentCurrMapper.findStudentCurr(id);
        Integer[] ids = list.toArray(new Integer[0]);

        Curriculum curriculum = curriculumService.getById(id);
        curriculum.setIds(ids);
        return curriculum;
    }

    //修改
    @PostMapping("/update")
    public Result update(@RequestBody Curriculum curriculum){
        //删除原有关联表数据
        QueryWrapper<StudentCurr> wrapper = new QueryWrapper<>();
        wrapper.eq("cid",curriculum.getId());
        studentCurrService.remove(wrapper);

        Integer[] ids = curriculum.getIds();
        for (Integer thisId : ids) {
            StudentCurr studentCurr = new StudentCurr();
            studentCurr.setSid(thisId);
            studentCurr.setCid(curriculum.getId());

            studentCurrService.save(studentCurr);
        }

        return curriculumService.updateById(curriculum)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    @GetMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") String id){
        return curriculumService.removeById(id)?Result.ok().message(ResultMessage.SUCCESSMSG):Result.error().message(ResultMessage.ERRORMSG);
    }

    //查询当前登录的教师执教的课程
    @GetMapping("/all")
    public List<Curriculum> findCurrByTid(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        Claims claim = jwtConfig.getTokenClaim(authorization);
        Integer uid = Integer.parseInt(claim.getSubject());

        Teacher teacher = teacherMapper.findTeacherByUid(uid.toString());

        QueryWrapper<Curriculum> wrapper = new QueryWrapper<>();
        wrapper.eq("tid",teacher.getId());

        return curriculumService.list(wrapper);
    }
}

