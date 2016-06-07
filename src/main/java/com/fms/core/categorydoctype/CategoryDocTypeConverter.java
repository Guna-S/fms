package com.fms.core.categorydoctype;

import com.fms.core.model.CategoryDocType;
import com.fms.core.model.UploadCategory;
import javaslang.Tuple2;

import java.util.function.Function;

public class CategoryDocTypeConverter {

    public static Function<UploadCategory, CategoryDocType> convert(
        final CategoryDocTypeInfo source) {
        return uc -> CategoryDocType.builder()
            .with(c -> c.getDesc(), source.getDesc())
            .with(c -> c.getType(), source.getType())
            .with(c -> c.getUploadCategory(), uc)
            .build();

    }

    public static CategoryDocTypeInfo convertTo(final CategoryDocType source) {
        return CategoryDocTypeInfo.builder()
            .on(c -> c.getId())
            .set(String.valueOf(source.getId()))
            .on(c -> c.getDesc())
            .set(source.getDesc())
            .on(c -> c.getType())
            .set(source.getType())
            .on(c -> c.getUploadCategoryName())
            .set(source.getUploadCategory().getName())
            .build();
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
