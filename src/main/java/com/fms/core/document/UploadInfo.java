package com.fms.core.document;

/**
 * Created by Ganesan on 03/06/16.
 * <p></p>
 */
public class UploadInfo {

    private Long documentTypeId;
    private String uploaderId;
    private String fileInfo;
    private String fileName;
    private Long fileSequence;

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

    public Long getFileSequence() {
        return fileSequence;
    }
}
