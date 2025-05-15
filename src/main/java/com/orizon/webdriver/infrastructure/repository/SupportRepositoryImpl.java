package com.orizon.webdriver.infrastructure.repository;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.SupportException;
import com.orizon.webdriver.domain.ports.repository.SupportRepository;
import com.orizon.webdriver.domain.model.Support;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class SupportRepositoryImpl implements SupportRepository {

    private final List<Support> supports;

    public SupportRepositoryImpl(){
        this.supports = new ArrayList<>();
    }

    public void listSupports(){
        supports.forEach(System.out::println);
    }

    @Override
    public void addSupport(Support supportRequest){
        Objects.requireNonNull(supportRequest, () -> {throw new ENFieldException();});
        supports.add(supportRequest);
    }

    @Override
    public void checkSupport(long id){
        Support support = supports.stream().filter(s -> s.getId() == id)
                .findFirst().orElseThrow(SupportException::new);

        support.changeSuportStatus();
        supports.remove(support);
    }

    @Override
    public void getAllSupports(){
        System.out.println(supports);
    }
}
