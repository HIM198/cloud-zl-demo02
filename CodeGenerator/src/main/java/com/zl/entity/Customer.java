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
 * @since 2022-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Customer对象", description="")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键	")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "账号编号")
    private Integer uid;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "级别")
    private String level;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}
