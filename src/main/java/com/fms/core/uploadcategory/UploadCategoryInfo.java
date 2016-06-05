package com.fms.core.uploadcategory;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("different type of categories model")
public class UploadCategoryInfo implements Serializable {

    private static final long serialVersionUID = -7597602634402038857L;

    @ApiModelProperty(value = "category name", required = true, example = "Student,Teacher")
    private String name;
    @ApiModelProperty(value = "description about the category", required = true)
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
