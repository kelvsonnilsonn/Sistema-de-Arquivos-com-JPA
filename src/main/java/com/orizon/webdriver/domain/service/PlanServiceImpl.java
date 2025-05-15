package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.InvalidPlanException;
import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.model.Plan;
import com.orizon.webdriver.domain.ports.repository.PlanRepository;
import com.orizon.webdriver.domain.ports.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Service
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;

    @Autowired
    public PlanServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public void deletePlan(long id) {
        Plan plano = planRepository.getAllPlans().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new InvalidPlanException("Plano não encontrado"));

        planRepository.remove(plano);
    }

    @Override
    public Plan createPlan(String name, String userSpace) {
        Objects.requireNonNull(name, () -> {throw new ENFieldException();});
        Objects.requireNonNull(userSpace, () -> {throw new ENFieldException();});

        Plan plan = new Plan(name, userSpace);
        planRepository.add(plan);
        return plan;
    }

    @Override
    public void assignPlanToInstitution(Plan plan, Institution institution) {
        Objects.requireNonNull(plan, () -> {throw new ENFieldException();});
        Objects.requireNonNull(institution, () -> {throw new ENFieldException();});

        institution.setPlano(plan);
    }

    @Override
    public void updatePlanDuration(Institution institution, Duration newDuration) {
        Objects.requireNonNull(institution, () -> {throw new ENFieldException();});

        if (institution.getPlano() == null) {
            throw new InvalidPlanException("Instituição não possui um plano associado");
        }

        institution.getPlano().setDuration(newDuration);
    }

    @Override
    public void updatePlanUserSpace(Institution institution, String newUserSpace) {
        Objects.requireNonNull(institution, () -> {throw new ENFieldException();});
        Objects.requireNonNull(newUserSpace, () -> {throw new ENFieldException();});

        if (institution.getPlano() == null) {
            throw new InvalidPlanException("Instituição não possui um plano associado");
        }

        institution.getPlano().setUserSpace(newUserSpace);
    }

    @Override
    public Plan getInstitutionPlan(Institution institution) {
        Objects.requireNonNull(institution, () -> {throw new ENFieldException();});
        return institution.getPlano();
    }

    @Override
    public List<Plan> getAllAvailablePlans() {
        return planRepository.getAllPlans();
    }
}