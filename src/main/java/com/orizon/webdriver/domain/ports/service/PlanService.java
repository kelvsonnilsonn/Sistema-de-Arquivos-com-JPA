package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.model.Plan;

import java.time.Duration;

public interface PlanService {
    void listAll();
    Plan findOne(Long id);
    void create(String name, int userspace);
    void delete(Long id);
    void update(Plan plan);
    void updatePlanName(Long id, String name);
    void assignPlanToInstitution(Plan plan, Institution institution);
    void updatePlanDuration(Long id, Long newDuration);
    void updatePlanUserSpace(Long id, int newUserSpace);
    Plan getInstitutionPlan(Institution institution);
}