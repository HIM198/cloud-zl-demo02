package com.zl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2023-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Tractor对象", description="")
public class Tractor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "机器编号")
    private String machineNo;

    @ApiModelProperty(value = "油耗值")
    private String oilCon;

    @ApiModelProperty(value = "负荷系数")
    private String loadFactor;

    @ApiModelProperty(value = "行驶速度")
    private String travelSpeed;

    @ApiModelProperty(value = "本班田间工作时间")
    private String workTime;

    @ApiModelProperty(value = "本班运输工作时间")
    private String transportTime;

    @ApiModelProperty(value = "累计田间工作时间")
    private String sumWorkTime;

    @ApiModelProperty(value = "累计运输作业时间")
    private String sumTransportTime;

    @ApiModelProperty(value = "本班空转时间")
    @TableField("Idling_time")
    private String idlingTime;

    @ApiModelProperty(value = "本班累计油耗")
    private String sumOil;

    @ApiModelProperty(value = "总累计油耗")
    private String sumMachineOil;

    @ApiModelProperty(value = "班小时耗油量")
    private String hourOil;

    @ApiModelProperty(value = "本班田间作业量")
    private String fieldTask;

    @ApiModelProperty(value = "本班运输作业量")
    private String transportTask;

    @ApiModelProperty(value = "作业模式显示")
    private String pattern;

    @ApiModelProperty(value = "本班平均负荷系数")
    private String loadCoefficient;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "地面航向")
    private String groundCourse;

    private String value1;

    private String value2;

    private String value3;

    private String value4;

    private String value5;

    private String createTime;

    private String createBy;


}
