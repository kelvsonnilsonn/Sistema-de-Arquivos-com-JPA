package com.orizon.webdriver.domain.ports.repository;

import com.orizon.webdriver.domain.model.Plan;
import java.util.List;

public interface PlanRepository {
    List<Plan> getAllPlans();  // Deve retornar List<Plan> em vez de void
    void add(Plan plan);
    void remove(Plan plan);
}