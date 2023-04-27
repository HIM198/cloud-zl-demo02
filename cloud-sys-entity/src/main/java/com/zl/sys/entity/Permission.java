package com.zl.sys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author XW.Fan
 * @since 2022-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Permission对象", description="")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单id")
    @TableId
    private String id;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "排序")
    private Integer ordernum;

    @ApiModelProperty(value = "请求地址")
    private String url;

    @ApiModelProperty(value = "父id")
    private String parentId;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField(exist = false)
    private List<Permission> children = new ArrayList<>();

}
