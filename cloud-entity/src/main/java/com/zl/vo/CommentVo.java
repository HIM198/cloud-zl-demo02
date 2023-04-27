package com.zl.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CommentVo {
    private Integer id;
    private String name;
    private String content;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String loginUser;

    private List<CommentVo> children=new ArrayList<>();
}
