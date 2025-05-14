package com.orizon.webdriver.infrastructure.repository;

import com.orizon.webdriver.domain.model.Institution;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class InstitutionRepository {

    private final List<Institution> institutions;

    public void getAllInstitutions(){
        System.out.println(
                institutions.isEmpty() ?
                        "⛔ Nenhuma instituição cadastrada\n" :
                        "📋 Instituições cadastradas:\n" +
                                institutions.stream()
                                        .map(Institution::toString)
                                        .collect(Collectors.joining("\n――――――――――――――――――\n"))
        );
    }

    public InstitutionRepository(){
        this.institutions = new ArrayList<>();
    }

    public void addInstitution(Institution institution){
        institutions.add(institution);
    }

    public void deleteInstitution(Institution institution){
        institutions.remove(institution);
    }

    public Institution institutionSearch(long id){
        return institutions.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
    }
}
