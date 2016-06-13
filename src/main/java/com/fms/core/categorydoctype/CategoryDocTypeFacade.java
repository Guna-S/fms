package com.fms.core.categorydoctype;

import com.fms.core.common.*;
import com.fms.core.config.FmsConfig;
import com.fms.core.model.CategoryDocType;
import com.fms.core.model.UploadCategory;
import com.fms.core.repository.CategoryDocTypeRepository;
import com.fms.core.repository.UploadCategoryRepository;
import javaslang.Tuple;

import java.util.List;
import java.util.function.Function;

import static com.fms.core.common.FunctionUtils.asList;
import static com.fms.core.common.FunctionUtils.asTwoTrack;
import static com.fms.core.uploadcategory.UploadCategoryFacade.findByName;

public class CategoryDocTypeFacade {

    /**
     *
     * @param info
     * @return
     */
    public static Reader<FmsConfig, Promise<TwoTrack<CategoryDocTypeInfo>>> save(final CategoryDocTypeInfo info) {
        return Reader.of(config -> React.of(info)
                .thenP(findUploadCategory().with(config.getUploadCategoryRepository()))
                .then(asTwoTrack(
                    uc -> CategoryDocTypeConverter.convert(info).apply(uc)))
                .then(asTwoTrack(
                    uc -> config.getCategoryDocTypeRepository().save(uc)))
                .then(asTwoTrack(
                    uc -> CategoryDocTypeConverter.convertTo(uc)))
                .getPromise());
    }

    /**
     *
     * @return
     */
    public static Reader<CategoryDocTypeRepository, Promise<TwoTrack<List<CategoryDocTypeInfo>>>> findAll() {
        return Reader.of(repo -> React.of(() -> repo.findAll())
                .then(asList(CategoryDocTypeConverter::convertTo))
                .then(TwoTrack::ofNullable)
                .getPromise());
    }

    /**
     *
     * @param id
     * @return
     */
    public static Reader<CategoryDocTypeRepository, Promise<TwoTrack<CategoryDocTypeInfo>>> find(final Long id) {
        final Reader<CategoryDocTypeRepository, Promise<TwoTrack<CategoryDocTypeInfo>>> reader =
            Reader.of(repo -> React.of(id)
                .then(repo::findOne)
                .then(TwoTrack::ofNullable)
                .then(FunctionUtils.asTwoTrack(CategoryDocTypeConverter::convertTo))
                .getPromise());
        return reader;
    }

    public static Reader<CategoryDocTypeRepository,Promise<TwoTrack<CategoryDocType>>> findCategoryDocType(
                                                                                                        final Long id) {
        return Reader.of(repo -> getCategoryDocTypeReact(id).with(repo).then(TwoTrack::ofNullable).getPromise());
    }

    private static Reader<CategoryDocTypeRepository, React<CategoryDocType>> getCategoryDocTypeReact(final Long id) {
        return Reader.of(repo -> React.of(() -> repo.findOne(id)));
    }

    private static Reader<UploadCategoryRepository, Function<CategoryDocTypeInfo, Promise<TwoTrack<UploadCategory>>>>
                                                                                                  findUploadCategory() {
        return Reader.of(repo ->  cdtInfo -> findByName(cdtInfo.getUploadCategoryName()).with(repo));
    }

    public static Reader<FmsConfig, Promise<TwoTrack<CategoryDocTypeInfo>>> update(final Long id,
                                                                                   final CategoryDocTypeInfo info) {
        return Reader.of(config -> React.of(info)
                .thenP(findUploadCategory().with(config.getUploadCategoryRepository()))
                .then(asTwoTrack(
                             uc -> CategoryDocTypeConverter.convertWithId(info).apply(Tuple.of(uc, id))))
                .then(asTwoTrack(
                             cdt -> config.getCategoryDocTypeRepository().saveAndFlush(cdt)))
                .then(asTwoTrack(
                             CategoryDocTypeConverter::convertTo))
                .getPromise());
    }

    public static Reader<CategoryDocTypeRepository, Promise<Long>> delete(final Long id) {
        return Reader.of(repo -> React.of(id)
                .thenV(repo::delete)
                .getPromise());
    }
}
