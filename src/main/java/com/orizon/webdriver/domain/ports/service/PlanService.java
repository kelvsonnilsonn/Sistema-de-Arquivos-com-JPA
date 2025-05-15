package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.model.Plan;
import java.time.Duration;
import java.util.List;

public interface PlanService {
    Plan createPlan(String name, String userSpace);
    void assignPlanToInstitution(Plan plan, Institution institution);
    void updatePlanDuration(Institution institution, Duration newDuration);
    void updatePlanUserSpace(Institution institution, String newUserSpace);
    Plan getInstitutionPlan(Institution institution);
    List<Plan> getAllAvailablePlans();
    void deletePlan(long id);
}