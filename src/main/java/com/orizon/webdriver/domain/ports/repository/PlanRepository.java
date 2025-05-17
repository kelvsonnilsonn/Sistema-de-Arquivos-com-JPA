package com.orizon.webdriver.domain.ports.repository;

import com.orizon.webdriver.domain.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
