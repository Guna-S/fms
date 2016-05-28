package com.fms.core.service;


import com.fms.core.model.CategoryDocType;
import com.fms.core.repository.CategoryDocTypeRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class CategoryDocTypeService {

    public static Function<CategoryDocTypeRepository, CompletableFuture<List<CategoryDocType>>> findAll() {
        return repository -> CompletableFuture.supplyAsync(repository::findAll);
    }

    public static Function<CategoryDocTypeRepository, CompletableFuture<CategoryDocType>> save(final CategoryDocType
                                                                                                    categoryDocType) {
        return repository -> CompletableFuture.supplyAsync(() -> repository.save(categoryDocType));
    }

    public static Function<CategoryDocTypeRepository, CompletableFuture<CategoryDocType>> update(final CategoryDocType
        categoryDocType) {
        return repository -> CompletableFuture.supplyAsync(() -> repository.saveAndFlush(categoryDocType));
    }

    public static Function<CategoryDocTypeRepository, CompletableFuture<CategoryDocType>> findById(final Long id) {
        return repository -> CompletableFuture.supplyAsync(() -> repository.findOne(id));
    }

    public static Function<CategoryDocTypeRepository, CompletableFuture<Void>> delete(final Long id) {
        return repository -> CompletableFuture.runAsync(() -> repository.delete(id));
    }
}
