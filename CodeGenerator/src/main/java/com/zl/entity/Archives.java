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
 * `archives`
 * </p>
 *
 * @author XW.Fan
 * @since 2023-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Archives对象", description="`archives`")
public class Archives implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "设备id")
    private Integer eid;

    @ApiModelProperty(value = "档案变更内容")
    private String context;

    @ApiModelProperty(value = "维护日期")
    private String createTime;

    @ApiModelProperty(value = "操作人")
    private String createBy;


}
