package com.zl.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TreeVo {
    private String id;
    private String label;
    private List<TreeVo> children = new ArrayList<>();
}
