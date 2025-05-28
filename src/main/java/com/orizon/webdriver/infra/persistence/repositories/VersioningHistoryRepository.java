package com.orizon.webdriver.infra.persistence.repositories;

import com.orizon.webdriver.domain.model.VersioningHistory;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

public interface VersioningHistoryRepository extends JpaRepository<VersioningHistory, Long> {
    Set<VersioningHistory> findByFileId(Long fileId);
}
