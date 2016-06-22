package com.fms.core.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.core.common.Builder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("document model")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentInfo implements Serializable {

    @ApiModelProperty(value = "document type id")
    private Long documentTypeId;
    @ApiModelProperty(value = "uploader id")
    private String uploaderId;
    @ApiModelProperty(value = "file information")
    private String fileInfo;
    @ApiModelProperty(value = "name of the file")
    private String fileName;
    @ApiModelProperty(value = "location of the file")
    private String fileLocation;
    @ApiModelProperty(value = "document id")
    private Long id;
    @ApiModelProperty(value = "file sequence")
    private Long fileSequence;


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

    public Long getFileSequence() {
        return fileSequence;
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
            .with(DocumentInfo::getUploaderId, info.getUploaderId())
            .with(DocumentInfo::getFileSequence,info.getFileSequence());
    }
}
