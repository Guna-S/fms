package com.fms.core.config;

import com.fms.core.repository.CategoryDocTypeRepository;
import com.fms.core.repository.DocumentRepository;
import com.fms.core.repository.UploadCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Ganesan on 01/06/16.
 */
@Component
public class FmsConfigImpl implements FmsConfig {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UploadCategoryRepository uploadCategoryRepository;

    @Autowired
    private CategoryDocTypeRepository categoryDocTypeRepository;

    @Value("${file.rootFolder}")
    private String rootFolder;


    @Override
    public DocumentRepository getDocumentRepository() {
        return documentRepository;
    }

    @Override
    public CategoryDocTypeRepository getCategoryDocTypeRepository() {
        return categoryDocTypeRepository;
    }

    @Override
    public UploadCategoryRepository getUploadCategoryRepository() {
        return uploadCategoryRepository;
    }

    public String getRootFolder() {
        return rootFolder;
    }
}
