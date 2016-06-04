package com.fms.core.categorydoctype;

import com.fms.core.common.Builder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "document type model")
public class CategoryDocTypeInfo implements Serializable {

    private static final long serialVersionUID = 251031229602284843L;
    @ApiModelProperty(value = "type of document", required = true)
    private String type;
    @ApiModelProperty(value = "category name of document type", required = true, notes =
        "for example pan card document" +
            " type has student as category name")
    private String uploadCategoryName;
    @ApiModelProperty(value = "document type description", required = true)
    private String desc;
    @ApiModelProperty(value = "document type id")
    private String id;

    public CategoryDocTypeInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public String getUploadCategoryName() {
        return uploadCategoryName;
    }

    public String getDesc() {
        return desc;
    }

    public static Builder<CategoryDocTypeInfo> builder() {
        return Builder.of(CategoryDocTypeInfo.class);
    }
}
