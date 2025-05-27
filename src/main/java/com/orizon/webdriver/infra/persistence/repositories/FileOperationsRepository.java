package com.orizon.webdriver.infra.persistence.repositories;

import com.orizon.webdriver.domain.model.FileOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface FileOperationsRepository extends JpaRepository<FileOperation, Long> {

    Set<FileOperation> findByFileId(Long id);
    Set<FileOperation> findByUserId(Long id);
}
