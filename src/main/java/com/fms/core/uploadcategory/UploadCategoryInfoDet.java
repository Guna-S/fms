package com.fms.core.uploadcategory;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Ganesan on 06/06/16.
 */
@ApiModel("different type of categories model")
public class UploadCategoryInfoDet {

    @ApiModelProperty(value = "id", required = true, example = "Auto generated")
    private Long id;
    @ApiModelProperty(value = "category name", required = true, example = "Student,Teacher")
    private String name;
    @ApiModelProperty(value = "description about the category", required = true)
    private String desc;

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Long getId() {
        return id;
    }
}
