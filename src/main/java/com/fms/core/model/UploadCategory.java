package com.fms.core.model;

import com.fms.core.util.Builder;

import javax.persistence.*;

@Entity
@Table(name = "FMS_MA_UPLOAD_CATEGORY")
public class UploadCategory {

    @Id
    @GeneratedValue
    @Column(name = "UC_ID")
    private Long id;

    @Column(name = "UC_NAME")
    private String name;

    @Column(name = "UC_DESC")
    private String desc;


    public UploadCategory() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }


    public static Builder<UploadCategory> builder() {
        return Builder.of(UploadCategory.class);
    }
}
