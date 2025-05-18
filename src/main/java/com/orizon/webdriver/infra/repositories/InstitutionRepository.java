package com.orizon.webdriver.infra.repositories;

import com.orizon.webdriver.domain.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {
}
