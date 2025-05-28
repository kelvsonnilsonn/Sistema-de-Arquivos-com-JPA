package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.model.Plan;

import java.time.Duration;

public interface PlanService {
    void findAll();
    Plan findById(Long id);
    void create(String name, int userspace);
    void delete(Long id);
    void update(Plan plan);
    void updateName(Long id, String name);
    void assignToInstitution(Long planId, Long institutionId);
    void updateDuration(Long id, Long newDuration);
    void updateUserSpace(Long id, int newUserSpace);
}