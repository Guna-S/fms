package com.fms.core.repository;

import com.fms.core.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("InterfaceNeverImplemented")
public interface DocumentRepository extends JpaRepository<Document,Long> {
}
