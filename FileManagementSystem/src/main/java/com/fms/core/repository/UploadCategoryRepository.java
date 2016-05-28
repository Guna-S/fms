package com.fms.core.repository;

import com.fms.core.model.UploadCategory;
import org.springframework.data.jpa.repository.JpaRepository;


@SuppressWarnings("InterfaceNeverImplemented")
public interface UploadCategoryRepository extends JpaRepository<UploadCategory, Long> {

    /**
     *
     * @param name
     * @return
     */
    UploadCategory findByName(final String name);
}
