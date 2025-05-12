package com.orizon.webdriver.domain.model.user.userdata;

import com.orizon.webdriver.exceptions.ShortPasswordException;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Password{
    private String pass;

    public Password(String pass){
        validate(pass);
        this.pass = pass;
    }

    private void validate(String pass){
        if(pass.length() < 3)
            throw new ShortPasswordException();
        Objects.requireNonNull(pass, "A senha não pode ser nula.");
    }
}
