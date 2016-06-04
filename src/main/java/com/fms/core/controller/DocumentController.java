package com.fms.core.controller;

import com.fms.core.config.FmsConfig;
import com.fms.core.document.DocumentConfig;
import com.fms.core.document.DocumentInfo;
import com.fms.core.document.UploadInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.fms.core.DeferredResultProvider.createDeferredResult;
import static com.fms.core.DeferredResultProvider.createDeferredResultTwoTrack;
import static com.fms.core.document.DocumentFacade.*;
import static com.fms.core.document.DocumentUtil.fileWritter;

@RestController
@Api(value = "documentController", description = "controller has all the document related api's")
public class DocumentController {

    @Autowired
    private FmsConfig fmsConfig;

    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        value = "upload document api",
        notes = "upload document api"
    )
    @RequestMapping(value = "/upload",
        method = RequestMethod.POST,
        headers = "Content-Type=multipart/form-data")
    public DeferredResult<ResponseEntity<DocumentInfo>> upload(
        @ApiParam(value = "document")
        @RequestParam("file") final MultipartFile file,
        @ApiParam(value = "info related to documents")
        @RequestPart("docinfo")
        final UploadInfo docInfo) {
        return createDeferredResultTwoTrack(
            save(docInfo)
                .with(DocumentConfig
                    .builder()
                    .with(DocumentConfig::getFmsConfig, fmsConfig)
                    .with(DocumentConfig::getFileWriter, fileWritter(file))
                    .build()), HttpStatus.ACCEPTED);
    }

    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        value = "List all the documents for given uploader id",
        notes = "List All the documents for given uploader id"
    )
    @RequestMapping(value = "/documents", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<List<DocumentInfo>>> getAllDocuments(
        @ApiParam(value = "uploader id for the documents", name = "uploaderId")
        @RequestBody final String uploaderId) {
        return createDeferredResult(documents(uploaderId).with(fmsConfig.getDocumentRepository()), HttpStatus.FOUND);
    }

    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        value = "remove the document for given uploader id",
        notes = "remove the document for given uploader ids"
    )
    @RequestMapping(value = "/delete/{docId}", method = RequestMethod.DELETE)
    public DeferredResult<ResponseEntity<Long>> remove(
        @ApiParam(value = "document id", name = "documentId")
        @PathVariable final Long id) {
        return createDeferredResult(removeFile(id).with(fmsConfig.getDocumentRepository()), HttpStatus.OK);
    }
}
