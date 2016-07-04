package com.fms.core.controller;

import com.fms.core.config.FmsConfig;
import com.fms.core.document.DocumentConfig;
import com.fms.core.document.DocumentInfo;
import com.fms.core.document.UploadInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
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
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        value = "upload document api",
        notes = "upload document api"
    )
    @RequestMapping(value = "/upload",
        method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<DocumentInfo>> upload(
        @ApiParam(value = "document as file",name="file")
        @RequestParam("file") final MultipartFile file,
        @ApiParam(value = "info related to documents",name="uploadInfo")
        @RequestPart("uploadInfo")
        final UploadInfo uploadInfo) {
        return createDeferredResultTwoTrack(
            save(uploadInfo)
                .with(DocumentConfig
                    .builder()
                    .with(DocumentConfig::getFmsConfig, fmsConfig)
                    .with(DocumentConfig::getFileWriter, fileWritter(file))
                    .build()), HttpStatus.ACCEPTED);
    }

    @ApiOperation(
        produces = MediaType.APPLICATION_OCTET_STREAM_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        value = "download document from document id",
        notes = "download document from document id"
    )
    @RequestMapping(value = "/download/{docId}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<FileSystemResource>> download(
        @ApiParam(value = "document id",name = "docId")
        @PathVariable final Long docId) {
        return createDeferredResult(getFile(docId)
                .with(fmsConfig.getDocumentRepository()), HttpStatus.ACCEPTED);
    }


    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        value = "get all  the document for given uploader id",
        notes = "get all  the document for given uploader ids"
    )
    @RequestMapping(value = "/documents/{uploaderId}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<List<DocumentInfo>>> getAllDocuments(
        @ApiParam(name = "uploaderId",value = "uploader id of document ")
        @PathVariable final String uploaderId) {
        return createDeferredResult(documents(uploaderId).with(fmsConfig.getDocumentRepository()), HttpStatus.FOUND);
    }

    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        value = "remove the document for given document id",
        notes = "remove the document for given document id"
    )
    @RequestMapping(value = "/delete/{docId}", method = RequestMethod.DELETE)
    public DeferredResult<ResponseEntity<Long>> remove(
        @ApiParam(name = "docId",value = "document id")
        @PathVariable final Long docId){
        return createDeferredResult(removeFile(docId).with(fmsConfig.getDocumentRepository()),HttpStatus.OK);
    }
}
