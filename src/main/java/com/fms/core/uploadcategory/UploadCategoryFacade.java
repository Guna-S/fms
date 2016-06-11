package com.fms.core.uploadcategory;

import com.fms.core.common.*;
import com.fms.core.model.UploadCategory;
import com.fms.core.repository.UploadCategoryRepository;

import java.util.List;

import static com.fms.core.common.FunctionUtils.asList;

public class UploadCategoryFacade {

    /**
     *
     * @param id
     * @return
     */
    public static Reader<UploadCategoryRepository, Promise<TwoTrack<UploadCategoryInfoDet>>> find(final Long id) {
        return Reader.of(repo -> React.of(() -> repo.findOne(id))
            .then(uc -> TwoTrack.ofNullable(uc))
            .then(FunctionUtils.asTwoTrack(UploadCategoryConverter::convertToDet))
            .getPromise());

    }

    /**
     *
     * @param id
     * @return
     */
    public static Reader<UploadCategoryRepository, Promise<Long>> delete(final Long id) {
        return Reader.of(repo -> React.of(() -> id)
                .thenV(repo::delete)
                .getPromise());
    }

    /**
     *
     * @param uploadCategoryInfo
     * @return
     */
    public static Reader<UploadCategoryRepository, Promise<UploadCategoryInfoDet>> save(final UploadCategoryInfo uploadCategoryInfo) {
        return Reader.of(repo ->  React.of(() -> uploadCategoryInfo)
                .then(UploadCategoryConverter::convert)
                .then(repo::save)
                .then(UploadCategoryConverter::convertToDet)
                .getPromise());
    }

    /**
     *
     * @param id
     * @param uploadCategoryInfo
     * @return
     */
    public static Reader<UploadCategoryRepository, Promise<UploadCategoryInfoDet>> update(final Long id, final UploadCategoryInfo uploadCategoryInfo) {
        return Reader.of(repository -> React.of(() -> UploadCategoryConverter.convertWithId(uploadCategoryInfo, id))
                .then(repository::saveAndFlush)
                .then(UploadCategoryConverter::convertToDet)
                .getPromise());
    }

    /**
     *
     * @param name
     * @return
     */
    public static Reader<UploadCategoryRepository, Promise<TwoTrack<UploadCategory>>>  findByName(final String name) {
        return Reader.of(repository -> React.of(() -> name)
            .then(repository::findByName)
            .then(uc ->  TwoTrack.ofNullable(uc))
            .getPromise());
    }

    /**
     *
     * @return
     */
    public static Reader<UploadCategoryRepository, Promise<List<UploadCategoryInfoDet>>> findAll() {
        return Reader.of(repository -> React.of(() -> repository.findAll())
                .then(asList(UploadCategoryConverter::convertToDet))
                .getPromise());
    }
}
