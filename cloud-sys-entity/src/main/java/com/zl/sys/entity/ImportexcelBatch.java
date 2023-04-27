package com.zl.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2022-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ImportexcelBatch对象", description="")
public class ImportexcelBatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "导入id")
    @TableId(value = "id")
    private String id;

    @ApiModelProperty(value = "导入模板id")
    private String tid;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "文件地址")
    private String filePath;

    @ApiModelProperty(value = "导入时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "导入状态")
    private String status;

    @ApiModelProperty(value = "导入消息")
    private String importMessage;


}
