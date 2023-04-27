package com.zl.sys.controller.config.log;

import java.lang.annotation.*;


@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented    
public  @interface SystemControllerNoLog {    
}  
