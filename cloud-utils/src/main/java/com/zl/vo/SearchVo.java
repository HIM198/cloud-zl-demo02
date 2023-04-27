package com.zl.vo;

import lombok.Data;

@Data
public class SearchVo {
    private Integer pageNumber;
    private Integer pageCount;

    private String valueOne;
    private String valueTwo;
    private String valueThree;
    private String valueFour;
}
