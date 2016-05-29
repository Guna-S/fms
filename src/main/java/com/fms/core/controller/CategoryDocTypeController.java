package com.fms.core.controller;

import com.fms.core.dto.CategoryDocTypeInfo;
import com.fms.core.facade.CategoryDocTypeFacade;
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
@RequestMapping(value = "/fms/categorydoctypes")
@Api(value = "", description = "CategoryDocTypeController")
public class CategoryDocTypeController {


    @Autowired
    private CategoryDocTypeFacade facade;

    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        value = "Get All available categories")
    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<List<CategoryDocTypeInfo>>> getAllCategories() {
        return createDeferredResult(facade.findAll(), HttpStatus.OK);
    }

    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        value = "Get All available categories")
    @RequestMapping(method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<CategoryDocTypeInfo>> save(
        @ApiParam(name = "categoryDocTypeInfo", value = "new category " +
            "info json", required = true)
        @RequestBody final CategoryDocTypeInfo categoryDocTypeInfo) {
        return createDeferredResult(facade.save(categoryDocTypeInfo), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<CategoryDocTypeInfo>> get(@PathVariable final Long id) {
        return createDeferredResult(facade.find(id), HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<CategoryDocTypeInfo>> update(@PathVariable final Long id,
        @RequestBody final CategoryDocTypeInfo categoryDocTypeInfo) {
        return createDeferredResult(facade.update(id, categoryDocTypeInfo), HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public DeferredResult<ResponseEntity<Long>> remove(@PathVariable final Long id) {
        return createDeferredResult(facade.delete(id), HttpStatus.OK);
    }
}
