package com.zl.sys.controller.config.enums;

/**
*
* @return
* @author ZHD-xinwei.Fan
* @creed: 下载模板枚举类
* @date 2022/10/24 15:07
*/

public enum DownloadTemplate {
    HOUSE_IMPORT("40287c5d748a904a01748b716a630101","户级导入","户名称, 组名称");

    private String tid;
    private String name;
    private String title;

    DownloadTemplate(String tid, String name, String title) {
        this.tid = tid;
        this.name = name;
        this.title = title;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
