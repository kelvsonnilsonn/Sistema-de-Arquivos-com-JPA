package com.orizon.webdriver.domain.model.user;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.InstitutionLimitException;
import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.model.Support;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
@DiscriminatorValue("ADMIN")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Administrator extends AbstractUser {

    @OneToMany(mappedBy = "admin", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Support> resolvedSupports = new HashSet<>();

    public Administrator(String name, String email, String password) {
        super(name, email, password);
    }

    public void resolveSupportRequest(Support support){
        Objects.requireNonNull(support, () -> {throw new ENFieldException();});
        if(this.resolvedSupports.add(support)){
            support.setAdmin(this);
        }
    }
}