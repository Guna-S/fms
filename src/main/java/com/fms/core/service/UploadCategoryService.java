package com.fms.core.service;

import com.fms.core.model.UploadCategory;
import com.fms.core.repository.UploadCategoryRepository;
import com.fms.core.util.React;

import java.util.List;
import java.util.function.Function;

/**
 *
 */
public class UploadCategoryService {

    public static Function<UploadCategoryRepository,React<List<UploadCategory>>> findAll() {
        return (repository) -> React.of(repository::findAll);
    }

    public static Function<UploadCategoryRepository,React<UploadCategory>>
                                                                        save(final UploadCategory  uploadCategory) {
        return repository ->  React.of(uploadCategory).then(repository::save);
    }

    public static Function<UploadCategoryRepository,React<UploadCategory>> update(final UploadCategory uploadCategory) {
        return (repository) -> React.of(uploadCategory).then(repository::saveAndFlush);
    }

    public static Function<UploadCategoryRepository,React<UploadCategory>> findById(final Long id) {
        return (repository) -> React.of(id).then(repository::findOne);
    }

    public static Function<UploadCategoryRepository, React<Long>> delete(final Long id) {
        return (repository) -> React.of(id).thenWithVoid(repository::delete);
    }

    public static Function<UploadCategoryRepository, React<UploadCategory>> findByName(final String name) {
        return repository -> React.of(name).then(repository::findByName);
    }
}
