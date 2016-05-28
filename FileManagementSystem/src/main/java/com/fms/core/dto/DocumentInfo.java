package com.fms.core.dto;

import com.fms.core.util.Builder;

public class DocumentInfo {

    private String documentTypeId;
    private String uploaderId;
    private String fileInfo;
    private String fileName;
    private String fileLocation;

    public String getDocumentTypeId() {
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

    public static Builder<DocumentInfo> builder(){return Builder.of(DocumentInfo.class);}
}
