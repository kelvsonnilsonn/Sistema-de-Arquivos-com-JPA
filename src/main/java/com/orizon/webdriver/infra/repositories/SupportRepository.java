package com.orizon.webdriver.infra.repositories;

import com.orizon.webdriver.domain.model.Support;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportRepository extends JpaRepository<Support, Long> {
}
