package com.fms.core.converter;

import com.fms.core.dto.UploadCategoryInfo;
import com.fms.core.model.UploadCategory;

public class UploadCategoryConverter {

    public static UploadCategory convert(final UploadCategoryInfo source) {
        return UploadCategory.builder()
            .on(category -> category.getName())
            .set(source.getName())
            .on(category -> category.getDesc())
            .set(source.getDesc())
            .build();
    }

    public static UploadCategoryInfo convertTo(final UploadCategory source) {
        return new UploadCategoryInfo(source.getName(), source.getDesc());
    }

    public static UploadCategory convertWithId(final UploadCategoryInfo source, final Long id) {
        return UploadCategory.builder()
            .on(category -> category.getId())
            .set(id)
            .on(category -> category.getName())
            .set(source.getName())
            .on(category -> category.getDesc())
            .set(source.getDesc())
            .build();
    }

}
