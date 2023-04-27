package com.zl.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SLogOperatorall对象", description="")
public class SLogOperatorall implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String module;

    private String type;

    private String description;

    private String uri;

    private String method;

    private String sessionId;

    private String requestId;

    private String params;

    private String createBy;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private String createDate;

    private String beanName;

    private String beanMethod;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private String beginTime;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;

    private String exceptionCode;

    private String exceptionDetail;

    private long requestTime;

    private String result;

    private String url;

    private String osInfo;

    private String browserInfo;

    private String requestParams;


}
