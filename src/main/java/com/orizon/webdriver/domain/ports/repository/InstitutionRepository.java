package com.orizon.webdriver.domain.ports.repository;

import com.orizon.webdriver.domain.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {
}
