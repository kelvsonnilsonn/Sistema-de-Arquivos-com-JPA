package com.orizon.webdriver.domain.valueobjects;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class UserAccess{

    private String login;
    private Password password;
    private Email email;

    public UserAccess(String login, String email, String password){
        this.login = Objects.requireNonNull(login, () -> {throw new ENFieldException(this);});
        this.password = new Password(password);
        this.email = new Email(email);
    }

    public String getEmail() { return this.email.getEmail(); }
}