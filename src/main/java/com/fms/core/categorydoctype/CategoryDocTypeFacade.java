package com.fms.core.categorydoctype;

import static com.fms.core.common.FunctionUtils.*;
import static com.fms.core.uploadcategory.UploadCategoryFacade.*;

import com.fms.core.config.FmsConfig;
import com.fms.core.model.CategoryDocType;
import com.fms.core.model.UploadCategory;
import com.fms.core.repository.CategoryDocTypeRepository;

import com.fms.core.repository.UploadCategoryRepository;
import com.fms.core.common.Promise;
import com.fms.core.common.React;
import com.fms.core.common.Reader;
import javaslang.Tuple;

import java.util.List;
import java.util.function.Function;

public class CategoryDocTypeFacade {


    public static Reader<FmsConfig, Promise<CategoryDocTypeInfo>> save(final CategoryDocTypeInfo info) {
        return Reader.of(config -> React.of(info)
                .thenP(findUploadCategory().with(config.getUploadCategoryRepository()))
                .thenR(CategoryDocTypeConverter.convert(info))
                .then(cdt -> config.getCategoryDocTypeRepository().save(cdt))
                .then(CategoryDocTypeConverter::convertTo)
                .getPromise());
    }

    public static Reader<CategoryDocTypeRepository, Promise<List<CategoryDocTypeInfo>>> findAll() {
        return Reader.of(repo -> React.of(() -> repo.findAll())
                .then(asList(CategoryDocTypeConverter::convertTo))
                .getPromise());
    }

    public static Reader<CategoryDocTypeRepository, Promise<CategoryDocTypeInfo>> find(final Long id) {
        return Reader.of(repo -> React.of(id)
                .then(repo::findOne)
                .then(CategoryDocTypeConverter::convertTo)
                .getPromise());
    }

    public static Reader<CategoryDocTypeRepository,Promise<CategoryDocType>> findCategoryDocType(final Long id) {
        return Reader.of(repo -> getCategoryDocTypeReact(id).with(repo).getPromise());
    }

    private static Reader<CategoryDocTypeRepository, React<CategoryDocType>> getCategoryDocTypeReact(final Long id) {
        return Reader.of(repo -> React.of(() -> repo.findOne(id)));
    }

    private static Reader<UploadCategoryRepository, Function<CategoryDocTypeInfo, Promise<UploadCategory>>> findUploadCategory() {
        return Reader.of(repo ->  cdtInfo -> findByName(cdtInfo.getUploadCategoryName()).with(repo));
    }

    public static Reader<FmsConfig, Promise<CategoryDocTypeInfo>> update(final Long id, final CategoryDocTypeInfo info) {
        return Reader.of(config -> React.of(info)
                .thenP(findUploadCategory().with(config.getUploadCategoryRepository()))
                .thenR(cat -> CategoryDocTypeConverter.convertWithId(info).apply(Tuple.of(cat, id)))
                .then(cdt -> config.getCategoryDocTypeRepository().saveAndFlush(cdt))
                .then(CategoryDocTypeConverter::convertTo)
                .getPromise());
    }

    public static Reader<CategoryDocTypeRepository, Promise<Long>> delete(final Long id) {
        return Reader.of(repo -> React.of(id)
                .thenV(repo::delete)
                .getPromise());
    }
}
