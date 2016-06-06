package com.fms.core.controller;

import com.fms.core.repository.UploadCategoryRepository;
import com.fms.core.uploadcategory.UploadCategoryInfo;
import com.fms.core.uploadcategory.UploadCategoryInfoDet;
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
import static com.fms.core.uploadcategory.UploadCategoryFacade.*;

@RestController
@RequestMapping(value = "/categories")
@Api(value = "upload category", description = "UploadCategoryController")
public class UploadCategoryController {

    @Autowired
    private UploadCategoryRepository repository;

    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        value = "Get All available upload categories",
        notes = "Get all categories api")
    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<List<UploadCategoryInfoDet>>> getAllCategories() {
        return createDeferredResult(findAll()
            .with(repository), HttpStatus.OK);
    }

    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        value = "new upload category api",
        notes = "new upload category api")
    @RequestMapping(method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<UploadCategoryInfoDet>> saveCategory(
        @ApiParam(name = "uploadCategoryInfo", value = "new category " +
            "info json", required = true)
        @RequestBody final UploadCategoryInfo uploadCategoryInfo) {
        return createDeferredResult(save(uploadCategoryInfo).with(repository), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<UploadCategoryInfoDet>> get(@PathVariable final Long id) {
        return createDeferredResult(find(id).with(repository), HttpStatus.OK);
    }


    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        value = "update the category by id",
        notes = "update the category by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<UploadCategoryInfoDet>> updateCategorry(
        @ApiParam(value = "category id", name = "categoryId", required = true)
        @PathVariable final Long id,
        @ApiParam(value = "category info json with updated values", name = "uploadCategoryInfo", required = true)
        @RequestBody final UploadCategoryInfo uploadCategoryInfo) {
        return createDeferredResult(update(id, uploadCategoryInfo).with(repository), HttpStatus.OK);
    }


    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        value = "remove the category by id",
        notes = "remove the category by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public DeferredResult<ResponseEntity<Long>> remove(
        @ApiParam(value = "category id", name = "categoryId", required = true)
        @PathVariable final Long id) {
        return createDeferredResult(delete(id).with(repository), HttpStatus.OK);
    }
}
