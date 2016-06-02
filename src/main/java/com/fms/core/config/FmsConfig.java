package com.fms.core.config;

import com.fms.core.repository.CategoryDocTypeRepository;
import com.fms.core.repository.DocumentRepository;
import com.fms.core.repository.UploadCategoryRepository;

/**
 * Created by Ganesan on 01/06/16.
 */
public interface FmsConfig {

    DocumentRepository getDocumentRepository();

    CategoryDocTypeRepository getCategoryDocTypeRepository();

    UploadCategoryRepository getUploadCategoryRepository();

    String getRootFolder();

}
