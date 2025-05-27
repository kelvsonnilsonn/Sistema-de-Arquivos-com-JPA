package com.orizon.webdriver.infra.persistence.repositories;

import com.orizon.webdriver.domain.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
