package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.InvalidInstitutionException;
import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.valueobjects.Address;
import com.orizon.webdriver.infra.persistence.repositories.InstitutionRepository;
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
    public void create(String name, String socialCause){
        Objects.requireNonNull(name, () -> {throw new ENFieldException();});
        Objects.requireNonNull(socialCause, () -> {throw new ENFieldException();});
        Institution institution = new Institution(name, socialCause);
        institutionDAO.save(institution);
    }

    @Override
    public void findAll() {
        institutionDAO.findAll().forEach(System.out::println);
    }

    @Override
    public Institution findById(Long id) {
        return institutionDAO.findById(id).orElseThrow(InvalidInstitutionException::new);
    }


    @Override
    public void delete(Long id) {
        Objects.requireNonNull(id, () -> {throw new ENFieldException();});
        Institution institution = findById(id);
        institution.getUsers().forEach(u -> u.setInstitution(null));
        institutionDAO.deleteById(id);
    }

    @Override
    public void update(Institution institution) {
        Objects.requireNonNull(institution, () -> {throw new ENFieldException();});
        institutionDAO.save(institution);
    }

    @Override
    public void updateName(Long id, String name) {
        Objects.requireNonNull(id, () -> {throw new ENFieldException();});
        Objects.requireNonNull(name, () -> {throw new ENFieldException();});
        Institution institution = findById(id);
        institution.setName(name);
        update(institution);
    }

    @Override
    public void updateSocialCause(Long id, String socialCause) {
        Objects.requireNonNull(id, () -> {throw new ENFieldException();});
        Objects.requireNonNull(socialCause, () -> {throw new ENFieldException();});
        Institution institution = findById(id);
        institution.setSocialCause(socialCause);
        update(institution);
    }

    @Override
    public void updateAddress(Long institutionId, String zipcode, String street, String number,
                              String neighborhood, String city, String state,
                              String country, String complement){
        Institution institution = findById(institutionId);

        Address newAddress = new Address();

        newAddress.setZipCode(zipcode);
        newAddress.setStreet(street);
        newAddress.setNumber(number);
        newAddress.setNeighborhood(neighborhood);
        newAddress.setCity(city);
        newAddress.setState(state);
        newAddress.setCountry(country);
        newAddress.setComplement(complement);

        institution.setAddress(newAddress);
        update(institution);
    }
}
