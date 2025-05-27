package com.orizon.webdriver.domain.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password{
    private String pass;

    public Password(String pass){
        validate(pass);
        this.pass = pass;
    }

    public static Password of(String pass) {
        return new Password(pass);
    }

    private void validate(String pass){
        if(pass.length() < 3)
            throw new IllegalArgumentException("Senha problema");
        Objects.requireNonNull(pass, "A senha não pode ser nula.");
    }
}
