package com.orizon.webdriver.infra.repositories;

import com.orizon.webdriver.domain.model.FileOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileOperationsRepository extends JpaRepository<FileOperation, Long> {

    List<FileOperation> findByFileId(Long id);
    List<FileOperation> findByUserId(Long id);
}
