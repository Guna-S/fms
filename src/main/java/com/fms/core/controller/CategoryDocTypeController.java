package com.fms.core.controller;

import com.fms.core.categorydoctype.CategoryDocTypeInfo;
import com.fms.core.config.FmsConfig;
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
import static com.fms.core.DeferredResultProvider.createDeferredResultTwoTrack;
import static com.fms.core.categorydoctype.CategoryDocTypeFacade.*;

@RestController
@RequestMapping(value = "/categorydoctypes")
@Api(value = "category doc types", description = "CRUD operations for category doc types")
public class CategoryDocTypeController {


    @Autowired
    private FmsConfig config;

    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        value = "Get All available category document types",
        notes = "Get All available category document types")
    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<List<CategoryDocTypeInfo>>> getAllCategories() {
        return createDeferredResultTwoTrack(findAll()
                .with(config.getCategoryDocTypeRepository()), HttpStatus.OK);
    }

    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        value = "save category doc type",
        notes = "save category doc type")
    @RequestMapping(method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<CategoryDocTypeInfo>> saveCDT(
        @ApiParam(name = "categoryDocTypeInfo", value = "new category " +
            "info json", required = true)
        @RequestBody final CategoryDocTypeInfo categoryDocTypeInfo) {
        return createDeferredResultTwoTrack(save(categoryDocTypeInfo)
                .with(config), HttpStatus.CREATED);
    }

    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        value = "get category doc type",
        notes = "get category doc type")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<CategoryDocTypeInfo>> get(
        @ApiParam(name = "categoryId", value = "category id", required = true)
        @PathVariable final Long id) {
        return createDeferredResultTwoTrack(find(id).with(config.getCategoryDocTypeRepository()), HttpStatus.OK);
    }

    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        value = "update category doc type with new values",
        notes = "update category doc type with new values")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<CategoryDocTypeInfo>> updateCDT(
        @ApiParam(name = "categoryId", value = "category id", required = true)
        @PathVariable final Long id,
        @ApiParam(name = "categoryDocTypeInfo", value = "updated values of category info json", required = true)
        @RequestBody final CategoryDocTypeInfo categoryDocTypeInfo) {
        return createDeferredResultTwoTrack(update(id, categoryDocTypeInfo)
                .with(config), HttpStatus.OK);
    }


    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        value = "remove the category doc type by id",
        notes = "remove the category doc type by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public DeferredResult<ResponseEntity<Long>> remove(
        @ApiParam(name = "categoryId", value = "category id", required = true)
        @PathVariable final Long id) {
        return createDeferredResult(delete(id)
                .with(config.getCategoryDocTypeRepository()), HttpStatus.OK);
    }
}
