package com.orizon.webdriver.domain.model.user;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.InstitutionLimitException;
import com.orizon.webdriver.domain.model.Institution;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Entity
@DiscriminatorValue("USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class User extends AbstractUser {
    public User(String name, String email, String password){
        super(name, email, password);
    }
}