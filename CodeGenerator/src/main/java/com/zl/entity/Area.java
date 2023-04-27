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
 * `area`
 * </p>
 *
 * @author XW.Fan
 * @since 2023-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Area对象", description="`area`")
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "场地名称")
    private String name;

    @ApiModelProperty(value = "类型")
    private String cate;


}
