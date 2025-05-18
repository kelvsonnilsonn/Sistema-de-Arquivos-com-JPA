package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.InvalidPlanException;
import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.model.Plan;
import com.orizon.webdriver.infra.repositories.PlanRepository;
import com.orizon.webdriver.domain.ports.service.PlanService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planDAO;

    @Autowired
    public PlanServiceImpl(PlanRepository planDAO) {
        this.planDAO = planDAO;
    }

    @Override
    public void listAll() {
        planDAO.findAll().forEach(System.out::println);
    }

    @Override
    public Plan findOne(Long id) {
        return planDAO.findById(id).orElseThrow(InvalidPlanException::new);
    }

    @Override
    public void save(Plan plan) {
        planDAO.save(plan);
    }

    @Override
    public void delete(Long id) {
        planDAO.deleteById(id);
    }

    @Override
    public void update(Plan plan) {
        planDAO.save(plan);
    }

//    public void save(String name, String userSpace) {
//        Objects.requireNonNull(name, () -> {throw new ENFieldException();});
//        Objects.requireNonNull(userSpace, () -> {throw new ENFieldException();});
//
//        Plan plan = new Plan(name, userSpace);
//        planRepository.add(plan);
//        return plan;
//    }


    @Override
    public void assignPlanToInstitution(Plan plan, Institution institution) {
        Objects.requireNonNull(plan, () -> {throw new ENFieldException();});
        Objects.requireNonNull(institution, () -> {throw new ENFieldException();});

        institution.setPlan(plan);
    }

    @Override
    public void updatePlanDuration(Institution institution, Long newDuration) {
        Objects.requireNonNull(institution, () -> {throw new ENFieldException();});
        Objects.requireNonNull(newDuration, () -> {throw new ENFieldException();});

        if (institution.getPlan() == null) {
            throw new InvalidPlanException("Instituição não possui um plano associado");
        }

        institution.getPlan().setDurationInSeconds(newDuration);
    }

    @Override
    public void updatePlanUserSpace(Institution institution, String newUserSpace) {
        Objects.requireNonNull(institution, () -> {throw new ENFieldException();});
        Objects.requireNonNull(newUserSpace, () -> {throw new ENFieldException();});

        if (institution.getPlan() == null) {
            throw new InvalidPlanException("Instituição não possui um plano associado");
        }

        institution.getPlan().setUserSpace(newUserSpace);
    }

    @Override
    public Plan getInstitutionPlan(Institution institution) {
        Objects.requireNonNull(institution, () -> {throw new ENFieldException();});
        return institution.getPlan();
    }
}