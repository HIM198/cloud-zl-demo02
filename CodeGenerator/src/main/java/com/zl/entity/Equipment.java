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
 * `equipment`
 * </p>
 *
 * @author XW.Fan
 * @since 2023-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Equipment对象", description="`equipment`")
public class Equipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "规格")
    private String spec;

    @ApiModelProperty(value = "品牌id")
    private Integer bid;

    @ApiModelProperty(value = "数量")
    private Integer num;


}
