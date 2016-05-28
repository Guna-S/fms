package com.fms.core.facade;

import com.fms.core.converter.CategoryDocTypeConverter;
import com.fms.core.dto.CategoryDocTypeInfo;
import com.fms.core.repository.CategoryDocTypeRepository;
import com.fms.core.service.CategoryDocTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
@Component
public class CategoryDocTypeFacade {

    @Autowired
    private CategoryDocTypeRepository repository;

    @Autowired
    private UploadCategoryFacade facade;

    public CompletableFuture<CategoryDocTypeInfo> save(final CategoryDocTypeInfo info) {
        return CategoryDocTypeConverter.convert(info)
            .apply(facade.findByName(info.getUploadCategoryName())).
                thenComposeAsync(
                    (categoryDocType) -> CategoryDocTypeService.save(categoryDocType).apply
                        (repository))
            .thenComposeAsync(
                (categoryDocType) ->
                    CompletableFuture.supplyAsync(() -> CategoryDocTypeConverter.convertTo(categoryDocType)));
    }

    public CompletableFuture<List<CategoryDocTypeInfo>> findAll() {
        return CategoryDocTypeService.findAll()
                                     .apply(repository)
                                     .thenComposeAsync(categoryDocTypes ->
                                         CompletableFuture.supplyAsync(() ->
                                             categoryDocTypes.stream()
                                                 .map(CategoryDocTypeConverter::convertTo)
                                                 .collect(Collectors.toList())));
    }

    public CompletableFuture<CategoryDocTypeInfo> find(final Long id) {
        return CategoryDocTypeService.findById(id)
                                      .apply(repository)
                                      .thenComposeAsync(
                                          categoryDocType -> CompletableFuture.supplyAsync(
                                              () -> CategoryDocTypeConverter.convertTo(categoryDocType)));
    }

    public CompletableFuture<CategoryDocTypeInfo> update(final Long id, final CategoryDocTypeInfo info) {
        return CategoryDocTypeConverter.convertWithId(info)
            .apply(facade.findByName(info.getUploadCategoryName()), id)
            .thenComposeAsync(
                (categoryDocType) -> CategoryDocTypeService.update(categoryDocType).apply
                    (repository))
            .thenComposeAsync(
                (categoryDocType) ->
                    CompletableFuture.supplyAsync(() -> CategoryDocTypeConverter.convertTo(categoryDocType)));
    }

    public CompletableFuture<Void> delete(final Long id) {
        return CategoryDocTypeService.delete(id).apply(repository);
    }
}
