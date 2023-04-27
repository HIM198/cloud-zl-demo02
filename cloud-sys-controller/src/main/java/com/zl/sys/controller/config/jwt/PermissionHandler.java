package com.zl.sys.controller.config.jwt;

import com.zl.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;

@RestControllerAdvice
public class PermissionHandler {
    @ExceptionHandler(value = { SignatureException.class })
    @ResponseBody
    public Result authorizationException(SignatureException e){
        return Result.error().message("您的token为空，请登录...");
    }
}
