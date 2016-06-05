package com.fms.core.controller;

import static com.fms.core.document.DocumentFacade.*;
import static com.fms.core.document.DocumentUtil.fileWritter;

import com.fms.core.config.FmsConfig;
import com.fms.core.document.DocumentConfig;
import com.fms.core.document.DocumentInfo;
import com.fms.core.document.UploadInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.fms.core.DeferredResultProvider.createDeferredResult;
import static com.fms.core.DeferredResultProvider.createDeferredResultTwoTrack;

@RestController
@RequestMapping("/fms")
public class DocumentController {

    @Autowired
    private FmsConfig fmsConfig;

    @RequestMapping(value = "/upload", method = RequestMethod.POST, headers="Content-Type=multipart/form-data")
    public DeferredResult<ResponseEntity<DocumentInfo>> upload(@RequestParam("file") MultipartFile file, @RequestPart("docinfo") UploadInfo docInfo) {
        return createDeferredResultTwoTrack(
            save(docInfo)
            .with(DocumentConfig
                    .builder()
                    .with(DocumentConfig::getFmsConfig, fmsConfig)
                    .with(DocumentConfig::getFileWriter, fileWritter(file))
                    .build()), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<FileSystemResource>> download(@PathVariable Long id) {
        return createDeferredResult(getFile(id)
                        .with(fmsConfig.getDocumentRepository()), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/documents/{uploaderid}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<List<DocumentInfo>>> getAllDocuments(@PathVariable final String uploaderid) {
        return createDeferredResult(documents(uploaderid).with(fmsConfig.getDocumentRepository()), HttpStatus.FOUND);
    }

    @RequestMapping(value = "/delete/{docid}",method = RequestMethod.DELETE)
    public DeferredResult<ResponseEntity<Long>> remove(@PathVariable final Long docid){
        return createDeferredResult(removeFile(docid).with(fmsConfig.getDocumentRepository()),HttpStatus.OK);
    }
}
