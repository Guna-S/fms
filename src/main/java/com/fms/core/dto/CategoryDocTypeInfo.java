package com.fms.core.dto;

import com.fms.core.util.Builder;

import java.io.Serializable;

public class CategoryDocTypeInfo implements Serializable {

    private static final long serialVersionUID = 251031229602284843L;
    private String type;
    private String uploadCategoryName;
    private String desc;
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
