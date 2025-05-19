package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.InvalidInstitutionException;
import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.model.Plan;
import com.orizon.webdriver.infra.repositories.InstitutionRepository;
import com.orizon.webdriver.domain.ports.service.InstitutionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
public class InstitutionServiceImpl implements InstitutionService {

    private final InstitutionRepository institutionDAO;

    @Autowired
    public InstitutionServiceImpl(InstitutionRepository institutionDAO) {
        this.institutionDAO = institutionDAO;
    }

    @Override
    public void listAll() {
        institutionDAO.findAll().forEach(System.out::println);
    }

    @Override
    public Institution findOne(Long id) {
        return institutionDAO.findById(id).orElseThrow(InvalidInstitutionException::new);
    }

    @Override
    public void create(String name, String socialCause){
        Objects.requireNonNull(name, () -> {throw new ENFieldException();});
        Objects.requireNonNull(socialCause, () -> {throw new ENFieldException();});
        Institution institution = new Institution(name, socialCause);
        institutionDAO.save(institution);
    }

    @Override
    public void delete(Long id) {
        Objects.requireNonNull(id, () -> {throw new ENFieldException();});
        Institution institution = findOne(id);
        institution.getUsers().forEach(u -> u.setInstitution(null));
        institutionDAO.deleteById(id);
    }

    @Override
    public void update(Institution institution) {
        Objects.requireNonNull(institution, () -> {throw new ENFieldException();});
        institutionDAO.save(institution);
    }

    @Override
    public void updateInstitutionName(Long id, String name) {
        Objects.requireNonNull(id, () -> {throw new ENFieldException();});
        Objects.requireNonNull(name, () -> {throw new ENFieldException();});
        Institution institution = findOne(id);
        institution.setName(name);
        update(institution);
    }

    @Override
    public void updateInstitutionSocialCause(Long id, String socialCause) {
        Objects.requireNonNull(id, () -> {throw new ENFieldException();});
        Objects.requireNonNull(socialCause, () -> {throw new ENFieldException();});
        Institution institution = findOne(id);
        institution.setSocialCause(socialCause);
        update(institution);
    }

}
