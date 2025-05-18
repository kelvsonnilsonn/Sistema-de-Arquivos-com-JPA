package com.orizon.webdriver.infra.repositories;

import com.orizon.webdriver.domain.model.VersioningHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersioningHistoryRepository extends JpaRepository<VersioningHistory, Long> {
}
