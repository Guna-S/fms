package com.fms.core.service;

import com.fms.core.model.Document;
import com.fms.core.repository.DocumentRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Created by Ram on 29-05-2016.
 */
public class DocumentService {

    public static Function<DocumentRepository, CompletableFuture<Document>> save(final Document document) {
        return documentRepository -> CompletableFuture.supplyAsync(() -> documentRepository.save(document));
    }

    public static Function<DocumentRepository, CompletableFuture<List<Document>>> findAll(final String uploadedId) {
        return documentRepository -> CompletableFuture.supplyAsync(() -> documentRepository.findByDocumentUploaderId(uploadedId));
    }

}
