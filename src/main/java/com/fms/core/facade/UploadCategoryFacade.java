package com.fms.core.facade;

import static com.fms.core.util.FunctionUtils.*;

import com.fms.core.converter.UploadCategoryConverter;
import com.fms.core.dto.UploadCategoryInfo;
import com.fms.core.model.UploadCategory;
import com.fms.core.repository.UploadCategoryRepository;
import com.fms.core.util.Promise;
import com.fms.core.util.React;

import java.util.List;
import java.util.function.Function;

public class UploadCategoryFacade {

    /**
     *
     * @param id
     * @return
     */
    public static Function<UploadCategoryRepository, Promise<UploadCategoryInfo>> find(final Long id) {
        return repo -> React.of(() -> repo.findOne(id))
                .then(UploadCategoryConverter::convertTo)
                .getPromise();

    }

    /**
     *
     * @param id
     * @return
     */
    public static Function<UploadCategoryRepository, Promise<Long>> delete(final Long id) {
        return repo -> React.of(() -> id)
                .thenV(repo::delete)
                .getPromise();
    }

    /**
     *
     * @param uploadCategoryInfo
     * @return
     */
    public static Function<UploadCategoryRepository, Promise<UploadCategoryInfo>> save(final UploadCategoryInfo uploadCategoryInfo) {
        return repo ->  React.of(() -> uploadCategoryInfo)
                .then(UploadCategoryConverter::convert)
                .then(repo::save)
                .then(UploadCategoryConverter::convertTo)
                .getPromise();
    }

    /**
     *
     * @param id
     * @param uploadCategoryInfo
     * @return
     */
    public static Function<UploadCategoryRepository, Promise<UploadCategoryInfo>> update(final Long id, final UploadCategoryInfo uploadCategoryInfo) {

        return repository -> React.of(() -> UploadCategoryConverter.convertWithId(uploadCategoryInfo, id))
                .then(repository::saveAndFlush)
                .then(UploadCategoryConverter::convertTo)
                .getPromise();
    }

    /**
     *
     * @param name
     * @return
     */
    public static Function<UploadCategoryRepository, Promise<UploadCategory>>  findByName(final String name) {
        return repository -> React.of(() -> name).then(repository::findByName).getPromise();
    }

    /**
     *
     * @return
     */
    public static Function<UploadCategoryRepository, Promise<List<UploadCategoryInfo>>> findAll() {
        return repository -> React.of(() -> repository.findAll())
                .then(asList(UploadCategoryConverter::convertTo))
                .getPromise();
    }
}
