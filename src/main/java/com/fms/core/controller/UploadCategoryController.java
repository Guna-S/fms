package com.fms.core.controller;

import com.fms.core.dto.UploadCategoryInfo;
import com.fms.core.facade.UploadCategoryFacade;
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
    private UploadCategoryFacade facade;

    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        value = "Get All available upload categories")
    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<List<UploadCategoryInfo>>> getAllCategories() {
        return createDeferredResult(facade.findAll(), HttpStatus.OK);
    }

    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        value = "")
    @RequestMapping(method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<UploadCategoryInfo>> save(
                                                     @ApiParam(name = "uploadCategoryInfo", value = "new category " +
                                                         "info json", required = true)
                                                     @RequestBody final UploadCategoryInfo uploadCategoryInfo) {
        return createDeferredResult(facade.save(uploadCategoryInfo), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<UploadCategoryInfo>> get(@PathVariable final Long id) {
        return createDeferredResult(facade.find(id), HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<UploadCategoryInfo>> update(@PathVariable final Long id,
        @RequestBody final UploadCategoryInfo uploadCategoryInfo) {
        return createDeferredResult(facade.update(id, uploadCategoryInfo), HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public DeferredResult<ResponseEntity<Long>> remove(@PathVariable final Long id) {
        return createDeferredResult(facade.delete(id), HttpStatus.OK);
    }
}
