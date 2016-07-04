package com.fms.core.model;

import com.fms.core.common.Builder;

import javax.persistence.*;

@Entity
@Table(name = "FMS_MA_CATEGORY_DOCTYPE")
@SequenceGenerator(name="fms_sq_cd", sequenceName = "fms_sq_cd")
public class CategoryDocType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fms_sq_cd")
    @Column(name = "CD_ID")
    private Long id;

    @Column(name = "CD_TYPE")
    private String type;

    @ManyToOne
    @JoinColumn(name = "CD_UC_ID")
    private UploadCategory uploadCategory;

    @Column(name = "CD_DESC")
    private String desc;

    @Column(name="CD_MULTIPLE")
    private Boolean multiple;


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

    public Boolean isMultiple() {
        return multiple;
    }

    public static Builder<CategoryDocType> builder() {
        return Builder.of(CategoryDocType.class);
    }

    public static Builder<CategoryDocType> builder(final CategoryDocType categoryDocType) {
        return Builder.of(CategoryDocType.class,categoryDocType);
    }
}
