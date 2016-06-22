package com.fms.core.document;

import com.fms.core.model.CategoryDocType;
import com.fms.core.model.DocumentModel;

import java.util.function.Function;

public class DocumentConverter {

    public static Function<CategoryDocType, DocumentModel> convert(
        final DocumentInfo info) {
        return cdt -> DocumentModel.builder()
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
                .on(d -> d.getFileSequence())
                .set(info.getFileSequence())
            .build();
    }

    public static DocumentInfo convertTo(final DocumentModel doc) {
        return DocumentInfo.builder()
                .on(d -> d.getId()).set(doc.getId())
                .on(d -> d.getDocumentTypeId()).set(doc.getCategoryDocType().getId())
                .on(d -> d.getUploaderId()).set(doc.getDocumentUploaderId())
                .on(d -> d.getFileInfo()).set(doc.getFileInfo())
                .on(d -> d.getFileLocation()).set(doc.getFileLocation())
                .on(d -> d.getFileName()).set(doc.getFileName())
                .on(d -> d.getFileSequence()).set(doc.getFileSequence())
            .build();
    }

    public static Function<String, DocumentInfo> build(final DocumentInfo info) {
        return fileLocation -> DocumentInfo.builder(info).on(in -> in.getFileLocation()).set(fileLocation).build();
    }
}
