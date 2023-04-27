package com.zl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author XW.Fan
 * @since 2022-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Individualization对象", description="")
public class Individualization implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户编号")
    private Integer uid;

    @ApiModelProperty(value = "个性化方案")
    private String context;

    @ApiModelProperty(value = "执行天数")
    private Integer day;

    @ApiModelProperty(value = "价格")
    private Integer price;

    @ApiModelProperty(value = "回复")
    private String reply;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}
