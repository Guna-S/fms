package com.fms.core.categorydoctype;

import com.fms.core.model.CategoryDocType;
import com.fms.core.model.UploadCategory;
import javaslang.Tuple2;

import java.util.function.Function;

public class CategoryDocTypeConverter {

    public static Function<UploadCategory, CategoryDocType> convert(
        final CategoryDocTypeInfo source) {
        return uc -> CategoryDocType.builder()
            .with(CategoryDocType::getDesc, source.getDesc())
            .with(CategoryDocType::getType, source.getType())
            .with(CategoryDocType::getUploadCategory, uc)
            .with(CategoryDocType::isMultiple,source.isMultiple())
            .build();

    }

    public static CategoryDocTypeInfo convertTo(final CategoryDocType source) {
        final CategoryDocTypeInfo build = CategoryDocTypeInfo.builder()
            .on(c -> c.getId()).set(String.valueOf(source.getId()))
            .on(c -> c.getDesc())
            .set(source.getDesc())
            .on(c -> c.getType())
            .set(source.getType())
            .on(c -> c.getUploadCategoryName())
            .set(source.getUploadCategory().getName())
            .on(c -> c.isMultiple())
            .set(source.isMultiple())
            .build();
        return build;
    }

    public static Function<Tuple2<UploadCategory, Long>, CategoryDocType> convertWithId(
        final CategoryDocTypeInfo source) {
        return (tuple) -> CategoryDocType
            .builder((convert(source).apply(tuple._1)))
            .on(c -> c.getId())
            .set(tuple._2)
            .build();
    }
}
