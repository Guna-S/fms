package com.fms.core.service;


import com.fms.core.model.CategoryDocType;
import com.fms.core.repository.CategoryDocTypeRepository;
import com.fms.core.util.React;

import java.util.List;
import java.util.function.Function;

public class CategoryDocTypeService {

    public static Function<CategoryDocTypeRepository, React<List<CategoryDocType>>> findAll() {
        return repo -> React.of(repo::findAll);
    }

    public static Function<CategoryDocTypeRepository, React<CategoryDocType>> save(final CategoryDocType
                                                                                                    categoryDocType) {
        return repo -> React.of(categoryDocType).then(repo::save);
    }

    public static Function<CategoryDocTypeRepository, React<CategoryDocType>> update(final CategoryDocType
        categoryDocType) {
        return repository -> React.of(categoryDocType).then(repository::saveAndFlush);
    }

    public static Function<CategoryDocTypeRepository, React<CategoryDocType>> findById(final Long id) {
        return repository -> React.of(id).then(repository::findOne);
    }

    public static Function<CategoryDocTypeRepository, React<Long>> delete(final Long id) {
        return repository -> React.of(id).thenWithVoid(repository::delete);
    }
}
