package com.fms.core.dto;

import java.io.Serializable;

public class CategoryDocTypeInfo implements Serializable {

    private static final long serialVersionUID = 251031229602284843L;
    private String type;
    private String uploadCategoryName;
    private String desc;
    private String id;

    public CategoryDocTypeInfo() {
    }

    public CategoryDocTypeInfo(final String type, final String uploadCategoryName, final String desc) {
        this.type = type;
        this.uploadCategoryName = uploadCategoryName;
        this.desc = desc;
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
}
