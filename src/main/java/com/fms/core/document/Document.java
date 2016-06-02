package com.fms.core.document;

import com.fms.core.common.Builder;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by rmurugaian on 6/1/2016.
 */
public class Document {

    private DocumentInfo documentInfo;
    private CommonsMultipartFile file;

    public DocumentInfo getDocumentInfo() {
        return documentInfo;
    }

    public CommonsMultipartFile getFile() {
        return file;
    }

    public static Builder<Document> builder() {
        return Builder.of(Document.class);
    }

    public static Builder<Document> builder(final Document info) {
        return Builder.of(Document.class, info);
    }

}
