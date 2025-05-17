package com.orizon.webdriver.domain.model.user;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.InstitutionLimitException;
import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.model.Support;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Entity
@DiscriminatorValue("ADMIN")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Administrator extends AbstractUser {

    public Administrator(String login, String email, String password) {
        super(login, email, password);
    }

    public void checkSupportRequest(Support supportRequest){
        //
    }

    public void assignToInstitution(Institution institution){
        Objects.requireNonNull(institution, () -> {throw new ENFieldException();});
        if(this.getInstitution() != null){
            throw new InstitutionLimitException();
        }
        this.setInstitution(institution);
        institution.addAdmin(this);
    }
}