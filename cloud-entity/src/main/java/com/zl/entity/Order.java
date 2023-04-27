package com.zl.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Order对象", description="")
@TableName("orderes")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "客户编号")
    private Integer uid;

    @ApiModelProperty(value = "总金额")
    private String sumMoney;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "改装进度")
    private String schedule;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private String pid;
}
