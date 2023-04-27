package com.zl.sys.controller.config.jwt;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SignatureException;

@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    @Resource
    private JwtConfig jwtConfig;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws SignatureException {

        String uri = request.getRequestURI();
        logger.info("请求地址:{}", uri);

        //过滤地址
        if (
            uri.contains("/user/login")||
            uri.contains(".css")||
            uri.contains(".woff")||
            uri.contains(".woff2")||
            uri.contains(".ttf")||
            uri.contains(".js")||
            uri.contains(".gif")||
            uri.contains(".jpg")||
            uri.contains(".png")||
            uri.contains("/styles")||
            uri.contains("/html")||
            uri.contains("/css")||
            uri.contains("/customer/save")||
            uri.contains("/hello")||
            uri.contains("/downloadFile")||
            uri.contains("/randomFindCaseByList")||
            uri.contains("/oss")||
            uri.contains("/upload")||
            uri.contains("/individualization/save")||
            uri.contains("/downloadExcelFileMb")||
            uri.contains("/downloadHouseData")||
            uri.contains("/getKaptchaImage")||
            uri.contains("/js")||
            uri.contains("/login2")||
            uri.contains("/index.html")||
            uri.contains("saveRolePermission")||
            uri.equals("/dashboard")||
            uri.equals("/styles/js/request_utils.js")
        ){
            return true;
        }

        //校验Authorization是否为空
        if (StringUtils.isNotEmpty(request.getHeader(jwtConfig.getHeader()))){
            String token = request.getHeader(jwtConfig.getHeader());
            logger.info("token:{}",token);
            try{
                Claims claims = jwtConfig.getTokenClaim(token);
                if(claims == null || jwtConfig.isTokenExpired(claims.getExpiration())){
                    //如果claims重定向到登录页面
                    response.setStatus(401);
                    PrintWriter writer = response.getWriter();
                    writer.println("<html>");
                    writer.println("<script>");
                    writer.println("window.open ('/html/login.html','_top')");
                    writer.println("</script>");
                    writer.println("</html>");
                    return false;
                }else {
                    //设置 identityId 用户身份ID
                    request.setAttribute("identityId", claims.getSubject());
                }
            }catch (Exception e){

            }
        }else {
            try {
                response.setStatus(401);
                //设置响应的数据格式及数据的编码
                response.setContentType("text/html;charset=utf-8");

                PrintWriter writer = response.getWriter();
                writer.println("<html>");
                writer.println("<script>");
                writer.println("window.open ('/html/login.html','_top')");
                writer.println("</script>");
                writer.println("</html>");
                return false;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }


}
