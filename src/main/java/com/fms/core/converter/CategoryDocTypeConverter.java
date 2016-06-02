package com.fms.core.converter;

import com.fms.core.dto.CategoryDocTypeInfo;
import com.fms.core.model.CategoryDocType;
import com.fms.core.model.UploadCategory;
import com.fms.core.util.React;
import javaslang.Tuple2;

import java.util.function.Function;
import java.util.function.Supplier;

public class CategoryDocTypeConverter {

    public static Function<UploadCategory, React<CategoryDocType>> convert(
        final CategoryDocTypeInfo source) {
        return category -> React
            .of(() -> category)
            .then(uc -> CategoryDocType.builder()
                .with(c -> c.getDesc(), source.getDesc())
                .with(c -> c.getType(), source.getType())
                .with(c -> c.getUploadCategory(), uc)
                .build());

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

    public static Function<Tuple2<UploadCategory, Long>, React<CategoryDocType>> convertWithId(
        final CategoryDocTypeInfo source) {
        return (tuple) -> React
            .of(convert(source).apply(tuple._1))
            .then(cd -> CategoryDocType
                .builder(cd)
                .on(c -> c.getId())
                .set(tuple._2)
                .build());
    }
}
