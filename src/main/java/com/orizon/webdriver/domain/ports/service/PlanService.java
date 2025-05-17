package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.model.Plan;

import java.time.Duration;

public interface PlanService {
    void listAll();
    Plan findOne(Long id);
    void save(Plan plan);
    void delete(Long id);
    void update(Plan plan);
    void assignPlanToInstitution(Plan plan, Institution institution);
    void updatePlanDuration(Institution institution, Long newDuration);
    void updatePlanUserSpace(Institution institution, String newUserSpace);
    Plan getInstitutionPlan(Institution institution);
}