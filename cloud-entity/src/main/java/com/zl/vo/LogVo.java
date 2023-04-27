package com.zl.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogVo {
    private Integer id;
    private String uri;
    private String method;
    private String params;
    private String createDate;
    private String beanName;
    private String beanMethod;
    private String beginTime;
    private String endTime;
    private String requestTime;
    private String result;
    private String url;
    private String osInfo;
    private String browserInfo;
}
