package com.orizon.webdriver.domain.model;


import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.valueobjects.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Institution {
    private long id;
    private String name;
    private String socialCause;
    private Address address;
    private final List<AbstractUser> users;
    private Plan plano;

    public Institution(String name, String socialCause){
        this.name = Objects.requireNonNull(name, () -> {throw new ENFieldException();});
        this.socialCause = Objects.requireNonNull(socialCause, () -> {throw new ENFieldException();});
        this.users = new ArrayList<>();
    }

    public void addInstitutionUser(AbstractUser user){
        users.add(user);
    }

    public void getInstitutionUsers(){
        users.forEach(System.out::println);
    }

    public void setZipCode(String zipcode){ this.address.setZipCode(zipcode);}

    @Override
    public String toString() {
        return String.format(
                """
                🏛️ Instituição: %s
                🆔 ID: %d
                🎯 Causa Social: %s
                📍 Endereço: %s
                👥 Usuários Vinculados: %d
                """,
                name,
                id,
                socialCause,
                address != null ? address.toString().replace("\n", "\n    ") : "Não informado",
                users.size()
        );
    }
}
