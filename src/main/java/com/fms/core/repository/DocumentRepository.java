package com.fms.core.repository;

import com.fms.core.model.DocumentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@SuppressWarnings("InterfaceNeverImplemented")
public interface DocumentRepository extends JpaRepository<DocumentModel,Long> {

    List<DocumentModel> findByDocumentUploaderId(final String documentUploaderId);
}
