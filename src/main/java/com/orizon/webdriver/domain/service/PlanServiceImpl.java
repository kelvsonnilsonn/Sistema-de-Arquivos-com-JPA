package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.InvalidPlanException;
import com.orizon.webdriver.domain.exceptions.PlanInvalidLimitException;
import com.orizon.webdriver.domain.exceptions.PlanLimitExcededException;
import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.model.Plan;
import com.orizon.webdriver.domain.ports.service.InstitutionService;
import com.orizon.webdriver.infra.persistence.repositories.InstitutionRepository;
import com.orizon.webdriver.infra.persistence.repositories.PlanRepository;
import com.orizon.webdriver.domain.ports.service.PlanService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planDAO;
    private final InstitutionRepository institutionDAO;

    @Autowired
    public PlanServiceImpl(PlanRepository planDAO, InstitutionRepository institutionDAO) {
        this.planDAO = planDAO;
        this.institutionDAO = institutionDAO;
    }

    @Override
    public void create(String name, int userspace) {
        Objects.requireNonNull(name, () -> {throw new ENFieldException();});
        Objects.requireNonNull(userspace, () -> {throw new ENFieldException();});
        Plan plan = new Plan(name, userspace);
        planDAO.save(plan);
    }

    @Override
    public void findAll() {
        planDAO.findAll().forEach(System.out::println);
    }

    @Override
    public Plan findById(Long id) {
        return planDAO.findById(id).orElseThrow(InvalidPlanException::new);
    }


    @Override
    public void delete(Long id) {
        Objects.requireNonNull(id, () -> {throw new ENFieldException();});
        Plan plan = findById(id);
        plan.getInstitutions().forEach(i -> i.setPlan(null));
        planDAO.deleteById(id);
    }

    @Override
    public void update(Plan plan) {
        Objects.requireNonNull(plan, () -> {throw new ENFieldException();});
        planDAO.save(plan);
    }

    @Override
    public void updateName(Long id, String name) {
        Objects.requireNonNull(id, () -> {throw new ENFieldException();});
        Objects.requireNonNull(name, () -> {throw new ENFieldException();});
        Plan plan = findById(id);
        plan.setName(name);
        update(plan);
    }

    @Override
    public void assignToInstitution(Long planId, Long institutionId) {
        Objects.requireNonNull(planId, () -> {throw new ENFieldException();});
        Objects.requireNonNull(institutionId, () -> {throw new ENFieldException();});
        Plan plan = findById(planId);
        Institution institution = institutionDAO.findById(institutionId).orElseThrow();
        plan.addInstitution(institution);
        plan.setAcquisitionDate(Instant.now());
        institution.setPlan(plan);

        institutionDAO.save(institution);
        update(plan);
    }

    @Override
    public void updateDuration(Long id, Long newDuration) {
        Objects.requireNonNull(id, () -> {throw new ENFieldException();});
        Objects.requireNonNull(newDuration, () -> {throw new ENFieldException();});

        Plan plan = findById(id);
        plan.setDurationInSeconds(newDuration);
        update(plan);
    }

    @Override
    public void updateUserSpace(Long id, int newUserSpace) {
        Objects.requireNonNull(id, () -> {throw new ENFieldException();});

        if (newUserSpace <= 0) {
            throw new PlanInvalidLimitException();
        }

        Plan plan = findById(id);
        if (newUserSpace < plan.getUserSpace()) {
            throw new PlanLimitExcededException();
        }
        plan.setUserSpace(newUserSpace);
        update(plan);
    }
}