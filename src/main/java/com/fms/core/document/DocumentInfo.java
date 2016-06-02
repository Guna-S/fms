package com.fms.core.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.core.common.Builder;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentInfo implements Serializable {

    private static final long serialVersionUID = -6390849593000747058L;
    private Long documentTypeId;
    private String uploaderId;
    private String fileInfo;
    private String fileName;
    private String fileLocation;
    private Long id;


    public Long getId() {
        return id;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public String getUploaderId() {
        return uploaderId;
    }

    public String getFileInfo() {
        return fileInfo;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public static Builder<DocumentInfo> builder() {
        return Builder.of(DocumentInfo.class);
    }

    public static Builder<DocumentInfo> builder(final DocumentInfo info) {
        return Builder.of(DocumentInfo.class, info);
    }

    public static Builder<DocumentInfo> builder(final UploadInfo info) {
        return builder()
                .with(DocumentInfo::getDocumentTypeId, info.getDocumentTypeId())
                .with(DocumentInfo::getFileInfo, info.getFileInfo())
                .with(DocumentInfo::getFileName, info.getFileName())
                .with(DocumentInfo::getUploaderId, info.getUploaderId());
    }
}
