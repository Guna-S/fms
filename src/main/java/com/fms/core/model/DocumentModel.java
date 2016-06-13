package com.fms.core.model;

import com.fms.core.common.Builder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FMS_TR_UPLOADED_DOCS", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"UD_DOC_ID", "UD_CD_ID","UD_FILE_SEQUENCE"})
})
public class DocumentModel implements Serializable {

    private static final long serialVersionUID = 3508481074333082181L;

    @Id
    @GeneratedValue
    @Column(name = "UD_ID")
    private Long id;

    @Column(name = "UD_DOC_ID")
    private String documentUploaderId;

    @ManyToOne
    @JoinColumn(name = "UD_CD_ID")
    private CategoryDocType categoryDocType;

    @Column(name = "UD_FILE_NAME")
    private String fileName;

    @Column(name = "UD_FILE_INFO")
    private String fileInfo;

    @Column(name = "UD_FILE_LOCATION")
    private String fileLocation;

    @Column(name = "UD_FILE_SEQUENCE")
    private Long fileSequence;


    public Long getId() {
        return id;
    }

    public String getDocumentUploaderId() {
        return documentUploaderId;
    }

    public CategoryDocType getCategoryDocType() {
        return categoryDocType;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileInfo() {
        return fileInfo;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public Long getFileSequence() {
        return fileSequence;
    }

    public static Builder<DocumentModel> builder() {
        return Builder.of(DocumentModel.class);
    }
}
