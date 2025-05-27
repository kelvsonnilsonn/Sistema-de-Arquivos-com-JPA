package com.orizon.webdriver.infra.persistence.repositories;

import com.orizon.webdriver.domain.model.VersioningHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface VersioningHistoryRepository extends JpaRepository<VersioningHistory, Long> {
}
