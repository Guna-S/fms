package com.fms.core.facade;

import static com.fms.core.util.FunctionUtils.*;
import static com.fms.core.facade.UploadCategoryFacade.*;

import com.fms.core.config.FmsConfig;
import com.fms.core.converter.CategoryDocTypeConverter;
import com.fms.core.dto.CategoryDocTypeInfo;
import com.fms.core.model.CategoryDocType;
import com.fms.core.model.UploadCategory;
import com.fms.core.repository.CategoryDocTypeRepository;

import com.fms.core.util.Promise;
import com.fms.core.util.React;
import javaslang.Tuple;

import java.util.List;
import java.util.function.Function;

public class CategoryDocTypeFacade {


    public static Function<FmsConfig, Promise<CategoryDocTypeInfo>> save(final CategoryDocTypeInfo info) {
        return (config) -> React.of(() -> info)
                .thenP(findUploadCategory(config))
                .thenR(CategoryDocTypeConverter.convert(info))
                .then(cdt -> config.getCategoryDocTypeRepository().save(cdt))
                .then(CategoryDocTypeConverter::convertTo)
                .getPromise();
    }

    public static Function<CategoryDocTypeRepository, Promise<List<CategoryDocTypeInfo>>> findAll() {
        return repo -> React.of(() -> repo.findAll())
                .then(asList(CategoryDocTypeConverter::convertTo))
                .getPromise();
    }

    public static Function<CategoryDocTypeRepository, Promise<CategoryDocTypeInfo>> find(final Long id) {
        return repo -> React.of(() -> id)
                .then(repo::findOne)
                .then(CategoryDocTypeConverter::convertTo)
                .getPromise();
    }

    public static Function<CategoryDocTypeRepository,Promise<CategoryDocType>> findCategoryDocType(final Long id) {
        return repo -> getCategoryDocTypeReact(id).apply(repo).getPromise();
    }

    private static Function<CategoryDocTypeRepository, React<CategoryDocType>> getCategoryDocTypeReact(final Long id) {
        return repo -> React.of(() -> repo.findOne(id));
    }

    private static Function<CategoryDocTypeInfo, Promise<UploadCategory>> findUploadCategory(FmsConfig config) {
        return cdtInfo -> findByName(cdtInfo.getUploadCategoryName()).apply(config.getUploadCategoryRepository());
    }

    public static Function<FmsConfig, Promise<CategoryDocTypeInfo>> update(final Long id, final CategoryDocTypeInfo info) {
        return config -> React.of(() ->info)
                .thenP(findUploadCategory(config))
                .thenR(cat -> CategoryDocTypeConverter.convertWithId(info).apply(Tuple.of(cat, id)))
                .then(cdt -> config.getCategoryDocTypeRepository().saveAndFlush(cdt))
                .then(CategoryDocTypeConverter::convertTo)
                .getPromise();
    }

    public static Function<CategoryDocTypeRepository, Promise<Long>> delete(final Long id) {
        return repo -> React.of(() -> id)
                .thenV(repo::delete)
                .getPromise();
    }
}
