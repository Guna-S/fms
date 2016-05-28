package com.fms.core.service;

import com.fms.core.model.UploadCategory;
import com.fms.core.repository.UploadCategoryRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 *
 */
public class UploadCategoryService {

    public static Function<UploadCategoryRepository,CompletableFuture<List<UploadCategory>>> findAll() {
        return (repository) -> CompletableFuture.supplyAsync(repository::findAll);
    }

    public static Function<UploadCategoryRepository,CompletableFuture<UploadCategory>>
                                                                        save(final UploadCategory  uploadCategory) {
        return repository ->  CompletableFuture.supplyAsync(() -> repository.save(uploadCategory));
    }

    public static Function<UploadCategoryRepository,CompletableFuture<UploadCategory>> update(final UploadCategory uploadCategory) {
        return (repository) -> CompletableFuture.supplyAsync(() -> repository.saveAndFlush(uploadCategory));
    }

    public static Function<UploadCategoryRepository,CompletableFuture<UploadCategory>> findById(final Long id) {
        return (repository) -> CompletableFuture.supplyAsync(() -> repository.findOne(id));
    }

    public static Function<UploadCategoryRepository, CompletableFuture<Void>> delete(final Long id) {
        return (repository) -> CompletableFuture.runAsync(() -> repository.delete(id));
    }

    public static Function<UploadCategoryRepository, CompletableFuture<UploadCategory>> findByName(final String name) {
        return repository -> CompletableFuture.supplyAsync(() -> repository.findByName(name));
    }
}
