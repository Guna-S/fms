package com.fms.core.facade;

import com.fms.core.converter.UploadCategoryConverter;
import com.fms.core.dto.UploadCategoryInfo;
import com.fms.core.model.UploadCategory;
import com.fms.core.repository.UploadCategoryRepository;
import com.fms.core.service.UploadCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class UploadCategoryFacade {

    @Autowired
    private UploadCategoryRepository repository;

    /**
     *
     * @param id
     * @return
     */
    public CompletableFuture<UploadCategoryInfo> find(final Long id) {
        return UploadCategoryService.findById(id)
            .apply(repository).thenComposeAsync((uc) ->
                CompletableFuture
                    .supplyAsync(() -> UploadCategoryConverter.convertTo(uc)));
    }

    /**
     *
     * @param id
     * @return
     */
    public CompletableFuture<Void> delete(final Long id) {
        return UploadCategoryService.delete(id).apply(repository);
    }

    /**
     *
     * @param uploadCategoryInfo
     * @return
     */
    public CompletableFuture<UploadCategoryInfo> save(final UploadCategoryInfo uploadCategoryInfo) {
        return CompletableFuture.supplyAsync(
            () -> UploadCategoryConverter.convert(uploadCategoryInfo))
            .thenComposeAsync(uploadCategory ->
                UploadCategoryService.save
                    (uploadCategory).apply(repository))
            .thenComposeAsync(uc ->
                CompletableFuture.supplyAsync(() -> UploadCategoryConverter.convertTo(uc)));
    }

    /**
     *
     * @param id
     * @param uploadCategoryInfo
     * @return
     */
    public CompletableFuture<UploadCategoryInfo> update(final Long id, final UploadCategoryInfo uploadCategoryInfo) {
        return CompletableFuture.supplyAsync(
            () -> UploadCategoryConverter.convertWithId(uploadCategoryInfo, id))
            .thenComposeAsync(
                uploadCategory ->
                    UploadCategoryService.update(uploadCategory)
                        .apply(repository))
            .thenComposeAsync(
                uc ->
                    CompletableFuture.supplyAsync(() ->
                        UploadCategoryConverter.convertTo(uc)));
    }

    /**
     *
     * @param name
     * @return
     */
    public CompletableFuture<UploadCategory> findByName(final String name) {
        return UploadCategoryService.findByName(name).apply(repository);
    }

    /**
     *
     * @return
     */
    public CompletableFuture<List<UploadCategoryInfo>> findAll() {
        return UploadCategoryService.findAll().apply(repository)
            .thenComposeAsync(uploadCategories ->
                CompletableFuture.supplyAsync(() -> uploadCategories
                    .stream().map(UploadCategoryConverter::convertTo).collect(Collectors.toList())));
    }
}
