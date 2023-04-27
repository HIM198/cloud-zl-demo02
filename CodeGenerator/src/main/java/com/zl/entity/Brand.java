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
 * `brand`
 * </p>
 *
 * @author XW.Fan
 * @since 2023-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Brand对象", description="`brand`")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "品牌")
    private String name;


}
