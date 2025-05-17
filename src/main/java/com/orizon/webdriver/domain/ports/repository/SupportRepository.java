package com.orizon.webdriver.domain.ports.repository;

import com.orizon.webdriver.domain.model.Support;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportRepository extends JpaRepository<Support, Long> {
}
