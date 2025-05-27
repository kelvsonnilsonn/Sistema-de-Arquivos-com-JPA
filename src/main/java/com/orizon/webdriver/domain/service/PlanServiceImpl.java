package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.InvalidPlanException;
import com.orizon.webdriver.domain.exceptions.PlanLimitExcededException;
import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.model.Plan;
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
    public void listAll() {
        planDAO.findAll().forEach(System.out::println);
    }

    @Override
    public Plan findOne(Long id) {
        return planDAO.findById(id).orElseThrow(InvalidPlanException::new);
    }

    @Override
    public void create(String name, int userspace) {
        Objects.requireNonNull(name, () -> {throw new ENFieldException();});
        Objects.requireNonNull(userspace, () -> {throw new ENFieldException();});
        Plan plan = new Plan(name, userspace);
        planDAO.save(plan);
    }

    @Override
    public void delete(Long id) {
        Objects.requireNonNull(id, () -> {throw new ENFieldException();});
        Plan plan = findOne(id);
        plan.getInstitutions().forEach(i -> i.setPlan(null));
        planDAO.deleteById(id);
    }

    @Override
    public void update(Plan plan) {
        Objects.requireNonNull(plan, () -> {throw new ENFieldException();});
        planDAO.save(plan);
    }

    @Override
    public void updatePlanName(Long id, String name) {
        Objects.requireNonNull(id, () -> {throw new ENFieldException();});
        Objects.requireNonNull(name, () -> {throw new ENFieldException();});
        Plan plan = findOne(id);
        plan.setName(name);
        update(plan);
    }

    @Override
    public void assignPlanToInstitution(Plan plan, Institution institution) {
        Objects.requireNonNull(plan, () -> {throw new ENFieldException();});
        Objects.requireNonNull(institution, () -> {throw new ENFieldException();});
        plan.addInstitution(institution);
        plan.setAcquisitionDate(Instant.now());
        institution.setPlan(plan);

        institutionDAO.save(institution);
        update(plan);
    }

    @Override
    public void updatePlanDuration(Long id, Long newDuration) {
        Objects.requireNonNull(id, () -> {throw new ENFieldException();});
        Objects.requireNonNull(newDuration, () -> {throw new ENFieldException();});

        Plan plan = findOne(id);
        plan.setDurationInSeconds(newDuration);
        update(plan);
    }

    @Override
    public void updatePlanUserSpace(Long id, int newUserSpace) {
        Objects.requireNonNull(newUserSpace, () -> {throw new ENFieldException();});
        Plan plan = findOne(id);
        if(plan.getUserSpace() > newUserSpace){
            throw new PlanLimitExcededException();
        }
        plan.setUserSpace(newUserSpace);
        update(plan);
    }

    @Override
    public Plan getInstitutionPlan(Institution institution) {
        Objects.requireNonNull(institution, () -> {throw new ENFieldException();});
        return institution.getPlan();
    }
}