package com.zl.vo;

import lombok.Data;

@Data
public class HouseInfoVo {
    private Integer id;
    private String houseName;
    private String townName;
    private String villName;
    private String teamName;
    private Integer environment;
    private Integer custom;
    private Integer finghtingFire;
    private Integer volunteer;
    private Integer sun;

    public HouseInfoVo() {
    }

    public HouseInfoVo(Integer id, String houseName, String townName, String villName, String teamName, Integer environment, Integer custom, Integer finghtingFire, Integer volunteer, Integer sun) {
        this.id = id;
        this.houseName = houseName;
        this.townName = townName;
        this.villName = villName;
        this.teamName = teamName;
        this.environment = environment;
        this.custom = custom;
        this.finghtingFire = finghtingFire;
        this.volunteer = volunteer;
        this.sun = sun;
    }
}
