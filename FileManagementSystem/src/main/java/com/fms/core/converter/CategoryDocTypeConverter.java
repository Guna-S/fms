package com.fms.core.converter;

import com.fms.core.dto.CategoryDocTypeInfo;
import com.fms.core.model.CategoryDocType;
import com.fms.core.model.UploadCategory;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CategoryDocTypeConverter {

    public static Function<CompletableFuture<UploadCategory>, CompletableFuture<CategoryDocType>> convert(
        final CategoryDocTypeInfo source) {

        return (future) -> future.thenComposeAsync(uploadCategory ->
            CompletableFuture.supplyAsync(() -> CategoryDocType.builder()
                .on(category -> category.getDesc())
                .set(source.getDesc())
                .on(category -> category.getType())
                .set(source.getType())
                .on(category -> category.getUploadCategory())
                .set(uploadCategory)
                .build()));

    }

    public static CategoryDocTypeInfo convertTo(final CategoryDocType source) {
        final CategoryDocTypeInfo categoryDocTypeInfo =
            new CategoryDocTypeInfo(source.getType(), source.getUploadCategory().getName(), source.getDesc());
        categoryDocTypeInfo.setId(String.valueOf(source.getId()));
        return categoryDocTypeInfo;
    }

    public static BiFunction<CompletableFuture<UploadCategory>, Long, CompletableFuture<CategoryDocType>> convertWithId(
        final CategoryDocTypeInfo source) {

        return (future, id) -> convert(source).apply(future)
            .thenCompose(categoryDocType ->
                CompletableFuture.supplyAsync(
                    () -> CategoryDocType.builder(categoryDocType)
                        .on(c -> c.getId())
                        .set(id)
                        .build())
            );
    }
}
