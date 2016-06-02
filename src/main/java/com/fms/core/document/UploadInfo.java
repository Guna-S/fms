package com.fms.core.document;

/**
 * Created by Ganesan on 03/06/16.
 */
public class UploadInfo {

    private Long documentTypeId;
    private String uploaderId;
    private String fileInfo;
    private String fileName;

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
}
