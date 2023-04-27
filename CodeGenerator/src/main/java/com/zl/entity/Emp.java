package com.zl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2023-02-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Emp对象", description="")
public class Emp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "生日")
    private String birthday;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String addr;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "状态")
    private String status;


}
