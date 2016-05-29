package com.fms.core.facade;

import com.fms.core.converter.CategoryDocTypeConverter;
import com.fms.core.dto.CategoryDocTypeInfo;
import com.fms.core.repository.CategoryDocTypeRepository;
import com.fms.core.service.CategoryDocTypeService;
import com.fms.core.util.FunctionComposer;
import com.fms.core.util.Promise;
import com.fms.core.util.React;
import javaslang.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryDocTypeFacade {

    @Autowired
    private CategoryDocTypeRepository repository;

    @Autowired
    private UploadCategoryFacade facade;

    public Promise<CategoryDocTypeInfo> save(final CategoryDocTypeInfo info) {
        return React.of(info)
                .thenWithReact(ci -> CategoryDocTypeConverter.convert(ci).apply(facade.findByName(ci.getUploadCategoryName())))
                .then(repository::save)
                .then(CategoryDocTypeConverter::convertTo)
                .getPromise();
    }

    public Promise<List<CategoryDocTypeInfo>> findAll() {
        return React.of(CategoryDocTypeService.findAll().apply(repository))
                .then(FunctionComposer.asList(CategoryDocTypeConverter::convertTo))
                .getPromise();
    }

    public Promise<CategoryDocTypeInfo> find(final Long id) {
        return React.of(id)
                .thenWithReact(i -> CategoryDocTypeService.findById(i).apply(repository))
                .then(CategoryDocTypeConverter::convertTo)
                .getPromise();
    }

    public Promise<CategoryDocTypeInfo> update(final Long id, final CategoryDocTypeInfo info) {
        return React.of(CategoryDocTypeConverter.convertWithId(info).apply(Tuple.of(facade.findByName(info.getUploadCategoryName()), id)))
                .thenWithReact(categoryDocType -> CategoryDocTypeService.update(categoryDocType).apply(repository))
                .then(CategoryDocTypeConverter::convertTo)
                .getPromise();
    }

    public Promise<Long> delete(final Long id) {
        return CategoryDocTypeService.delete(id).apply(repository)
                .getPromise();
    }
}
