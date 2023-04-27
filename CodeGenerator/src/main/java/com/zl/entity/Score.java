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
 * @since 2023-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Score对象", description="")
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer sid;

    private Integer tid;

    private String tname;

    private String course;

    private Integer pingshiScore;

    private Integer jieduanScore1;

    private Integer jieduanScore2;

    private Integer qimoScore;

    private String zongpingScore;

    private Date createTime;

    private Date updateTime;


}
