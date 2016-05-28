package com.fms.core.dto;

import java.io.Serializable;

public class UploadCategoryInfo implements Serializable {

    private static final long serialVersionUID = -7597602634402038857L;

    private String name;
    private String desc;


    public UploadCategoryInfo() {
    }

    public UploadCategoryInfo(final String name, final String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
