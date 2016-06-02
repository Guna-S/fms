package com.fms.core.controller;

import static com.fms.core.uploadcategory.UploadCategoryFacade.*;

import com.fms.core.uploadcategory.UploadCategoryInfo;

import com.fms.core.repository.UploadCategoryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

import static com.fms.core.DeferredResultProvider.createDeferredResult;

@RestController
@RequestMapping(value = "/fms/categories")
@Api(value = "upload category", description = "UploadCategoryController")
public class UploadCategoryController {

    @Autowired
    private UploadCategoryRepository repository;

    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        value = "Get All available upload categories")
    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<List<UploadCategoryInfo>>> getAllCategories() {
        return createDeferredResult(findAll()
                .with(repository), HttpStatus.OK);
    }

    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        value = "")
    @RequestMapping(method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<UploadCategoryInfo>> saveCategory(
                                                     @ApiParam(name = "uploadCategoryInfo", value = "new category " +
                                                         "info json", required = true)
                                                     @RequestBody final UploadCategoryInfo uploadCategoryInfo) {
        return createDeferredResult(save(uploadCategoryInfo).with(repository), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<UploadCategoryInfo>> get(@PathVariable final Long id) {
        return createDeferredResult(find(id).with(repository), HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<UploadCategoryInfo>> updateCategorry(@PathVariable final Long id,
        @RequestBody final UploadCategoryInfo uploadCategoryInfo) {
        return createDeferredResult(update(id, uploadCategoryInfo).with(repository), HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public DeferredResult<ResponseEntity<Long>> remove(@PathVariable final Long id) {
        return createDeferredResult(delete(id).with(repository), HttpStatus.OK);
    }
}
