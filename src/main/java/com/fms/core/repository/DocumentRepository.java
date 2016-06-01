package com.fms.core.repository;

import com.fms.core.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@SuppressWarnings("InterfaceNeverImplemented")
public interface DocumentRepository extends JpaRepository<Document,Long> {

    List<Document> findByDocumentUploaderId(final String documentUploaderId);
}
