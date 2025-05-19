package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.InvalidInstitutionException;
import com.orizon.webdriver.domain.model.Institution;
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
        if(findOne(id) != null){
            institutionDAO.deleteById(id);
        } else {
            throw new InvalidInstitutionException();
        }
    }

    @Override
    public void update(Long id, String name, String socialCause) {
        Institution institution = findOne(id);
        if(name == null && socialCause == null){
            return;
        }
        if(name == null && !socialCause.isBlank()){
            institution.setSocialCause(socialCause);
        }
        if (socialCause == null && !name.isBlank()){
            institution.setName(name);
        }
        institutionDAO.save(institution);
    }
}
