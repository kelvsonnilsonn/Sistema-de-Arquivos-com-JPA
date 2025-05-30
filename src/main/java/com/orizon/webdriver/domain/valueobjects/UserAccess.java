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

    @Embedded
    private Password password;

    @Embedded
    private Email email;

    public UserAccess(String email, String password){
        setPassword(password);
        setEmail(email);
    }

    private void setPassword(String pass) { this.password = Password.of(pass); }
    private void setEmail(String email) { this.email = Email.of(email); }

    public String getEmail() { return this.email.getEmail(); }
    public String getPassword()  { return this.password.getPass(); }

    @Override
    public String toString() {
        return "UserAccess{" +
                ", password=" + password.getPass() +
                ", email=" + email.getEmail() +
                '}';
    }
}