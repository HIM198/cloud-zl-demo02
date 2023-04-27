package com.zl.sys.controller.config.intercept;

import com.zl.sys.controller.config.jwt.JwtConfig;
import com.zl.sys.service.UserRoleService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import javax.annotation.Resource;

/**
*
* @return
* @author ZHD-xinwei.Fan
* @creed: 请描述这个类的作用...
* @date 2022/10/28 18:06
*/

@SuppressWarnings("all")
@ControllerAdvice
public class PublicAdvice {
    @Resource
    private JwtConfig jwtConfig;
    @Resource
    private UserRoleService userRoleService;

//    @ModelAttribute
//    public void addCommonModel(Model model, HttpServletRequest request) {
//        String authorization = request.getHeader("Authorization");
//        Claims claim = jwtConfig.getTokenClaim(authorization);
//        Integer uid = Integer.parseInt(claim.getSubject());
////        List<Permission> permission = userRoleService.findPermission(uid);
//        model.addAttribute("navs", userRoleService.findPermission(uid));
//        System.out.println("12312312312312");
//    }



}
