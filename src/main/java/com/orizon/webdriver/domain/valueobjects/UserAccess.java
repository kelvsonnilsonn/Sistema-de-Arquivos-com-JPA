package com.orizon.webdriver.domain.valueobjects;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAccess{

    private String login;

    @Embedded
    private Password password;

    @Embedded
    private Email email;

    public UserAccess(String login, String email, String password){
        this.login = Objects.requireNonNull(login, () -> {throw new ENFieldException(this);});
        setPassword(password);
        setEmail(email);
    }

    private void setPassword(String pass) { this.password = Password.of(pass); }
    private void setEmail(String email) { this.email = Email.of(email); }

    public String getEmail() { return this.email.getEmail(); }
}