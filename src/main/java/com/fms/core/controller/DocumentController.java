package com.fms.core.controller;

import com.fms.core.dto.Document;
import com.fms.core.dto.DocumentInfo;
import com.fms.core.facade.DocumentFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

import static com.fms.core.DeferredResultProvider.createDeferredResult;
import static com.fms.core.DeferredResultProvider.createDeferredResultTwoTrack;

@RestController
@RequestMapping("/fms")
public class DocumentController {

    @Autowired
    private DocumentFacade facade;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<DocumentInfo>> upload(@RequestBody final Document document) {
        return createDeferredResultTwoTrack(
            facade.save(document.getDocumentInfo())
            .apply(DocumentFacade.fileWritter(document)), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/documents", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<List<DocumentInfo>>> documents(@RequestBody final String uploaderId) {
        return createDeferredResult(facade.documents(uploaderId), HttpStatus.FOUND);
    }

    @RequestMapping(value = "/delete/{docId}",method = RequestMethod.DELETE)
    public DeferredResult<ResponseEntity<Long>> remove(@PathVariable final Long id){
        return createDeferredResult(facade.removeFile(id),HttpStatus.OK);
    }
}
