package com.orizon.webdriver.infrastructure.repository;

import com.orizon.webdriver.domain.model.Plan;
import com.orizon.webdriver.domain.ports.repository.PlanRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PlanRepositoryImpl implements PlanRepository {

    private final List<Plan> plans;

    public PlanRepositoryImpl() {
        this.plans = new ArrayList<>();
    }

    @Override
    public List<Plan> getAllPlans() {
        return new ArrayList<>(plans);
    }

    @Override
    public void add(Plan plan) {
        if (plan != null) {
            plans.add(plan);
        }
    }

    @Override
    public void remove(Plan plan) {
        plans.remove(plan);
    }
}