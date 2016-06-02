package com.fms.core.model;

import com.fms.core.common.Builder;

import javax.persistence.*;

@Entity
@Table(name = "FMS_MA_CATEGORY_DOCTYPE")
public class CategoryDocType {

    @Id
    @GeneratedValue
    @Column(name = "CD_ID")
    private Long id;

    @Column(name = "CD_TYPE")
    private String type;

    @ManyToOne
    @JoinColumn(name = "CD_UC_ID")
    private UploadCategory uploadCategory;

    @Column(name = "CD_DESC")
    private String desc;


    public CategoryDocType() {
    }


    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public UploadCategory getUploadCategory() {
        return uploadCategory;
    }

    public String getDesc() {
        return desc;
    }

    public static Builder<CategoryDocType> builder() {
        return Builder.of(CategoryDocType.class);
    }

    public static Builder<CategoryDocType> builder(final CategoryDocType categoryDocType) {
        return Builder.of(CategoryDocType.class,categoryDocType);
    }
}
