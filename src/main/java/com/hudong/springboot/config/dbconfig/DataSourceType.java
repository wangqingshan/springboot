package com.hudong.springboot.config.dbconfig;

/**
 * DataSourceType
 *
 * @Title: DataSourceType.java
 * @Copyright: Copyright (c) 2005
 * @Description:
 * @Company: 互动百科
 * @Created on 2018/8/23 16:15
 * @Author 90
 */
public enum DataSourceType {

    read("read", "从库"),
    write("write", "主库");

    private String type;

    private String name;

    DataSourceType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
