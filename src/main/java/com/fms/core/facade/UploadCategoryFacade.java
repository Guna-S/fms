package com.fms.core.facade;

import com.fms.core.converter.UploadCategoryConverter;
import com.fms.core.dto.UploadCategoryInfo;
import com.fms.core.model.UploadCategory;
import com.fms.core.repository.UploadCategoryRepository;
import com.fms.core.service.UploadCategoryService;
import com.fms.core.util.FunctionUtils;
import com.fms.core.util.Promise;
import com.fms.core.util.React;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UploadCategoryFacade {

    @Autowired
    private UploadCategoryRepository repository;

    /**
     * @param id
     * @return
     */
    public Promise<UploadCategoryInfo> find(final Long id) {
        return React.of(UploadCategoryService.findById(id).apply(repository))
            .then(UploadCategoryConverter::convertTo)
            .getPromise();

    }

    /**
     * @param id
     * @return
     */
    public Promise<Long> delete(final Long id) {
        return UploadCategoryService.delete(id).apply(repository).getPromise();
    }

    /**
     * @param uploadCategoryInfo
     * @return
     */
    public Promise<UploadCategoryInfo> save(final UploadCategoryInfo uploadCategoryInfo) {
        return React.of(uploadCategoryInfo)
            .then(UploadCategoryConverter::convert)
            .thenWithReact(uploadCategory -> UploadCategoryService.save
                (uploadCategory).apply(repository))
            .then(UploadCategoryConverter::convertTo)
            .getPromise();
    }

    /**
     * @param id
     * @param uploadCategoryInfo
     * @return
     */
    public Promise<UploadCategoryInfo> update(final Long id, final UploadCategoryInfo uploadCategoryInfo) {

        return React.of(() -> UploadCategoryConverter.convertWithId(uploadCategoryInfo, id))
            .thenWithReact(uploadCategory -> UploadCategoryService.update(uploadCategory).apply(repository))
            .then(UploadCategoryConverter::convertTo)
            .getPromise();
    }

    /**
     * @param name
     * @return
     */
    public Promise<UploadCategory> findByName(final String name) {
        return UploadCategoryService.findByName(name).apply(repository).getPromise();
    }

    /**
     * @return
     */
    public Promise<List<UploadCategoryInfo>> findAll() {
        return React.of(UploadCategoryService.findAll().apply(repository))
            .then(FunctionUtils.asList(UploadCategoryConverter::convertTo))
            .getPromise();
    }
}
