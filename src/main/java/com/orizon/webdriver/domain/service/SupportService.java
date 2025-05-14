package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.model.Support;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.ports.repository.SupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SupportService {

    private final SupportRepository supportRepository;

    @Autowired
    public SupportService(SupportRepository supportRepository){
        this.supportRepository = supportRepository;
    }

    public void addSupportRequest(AbstractUser user,String title, String body){
        Objects.requireNonNull(user, () -> {throw new ENFieldException();});
        Objects.requireNonNull(body, () -> {throw new ENFieldException();});
        Objects.requireNonNull(title, () -> {throw new ENFieldException();});
        Support support = new Support(user, title, body);
        user.addSupportRequest(support);
        supportRepository.addSupport(support);
    }

    public void checkSupport(AbstractUser user, long id){
        supportRepository.checkSupport(id);
    }

    public void getAllSupports(){
        supportRepository.getAllSupports();
    }
}
