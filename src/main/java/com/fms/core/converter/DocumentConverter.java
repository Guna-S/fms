package com.fms.core.converter;

import com.fms.core.dto.DocumentInfo;
import com.fms.core.model.CategoryDocType;
import com.fms.core.model.Document;

import java.util.function.Function;

public class DocumentConverter {

    public static Function<CategoryDocType, Document> convert(
        final DocumentInfo info) {
        return cdt -> Document.builder()
            .on(d -> d.getCategoryDocType())
            .set(cdt)
            .on(d -> d.getDocumentUploaderId())
            .set(info.getUploaderId())
            .on(d -> d.getFileInfo())
            .set(info.getFileInfo())
            .on(d -> d.getFileLocation())
            .set(info.getFileLocation())
            .on(d -> d.getFileName())
            .set(info.getFileName())
            .build();
    }

    public static DocumentInfo convertTo(final Document doc) {
        return DocumentInfo.builder()
            .with(d -> d.getId(),doc.getId())
            .on(d -> d.getDocumentTypeId())
            .set(doc.getCategoryDocType().getId())
            .on(d -> d.getUploaderId())
            .set(doc.getDocumentUploaderId())
            .on(d -> d.getFileInfo())
            .set(doc.getFileInfo())
            .on(d -> d.getFileLocation())
            .set(doc.getFileLocation())
            .on(d -> d.getFileName())
            .set(doc.getFileName())
            .build();
    }

    public static Function<String, DocumentInfo> build(final DocumentInfo info) {
        return fileLocation -> DocumentInfo.builder(info).on(in -> in.getFileLocation()).set(fileLocation).build();
    }
}
