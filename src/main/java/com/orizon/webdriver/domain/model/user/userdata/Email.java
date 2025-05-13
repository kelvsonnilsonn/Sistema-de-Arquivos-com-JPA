package com.orizon.webdriver.domain.model.user.userdata;

import com.orizon.webdriver.domain.model.dataregex.EmailRegex;
import com.orizon.webdriver.domain.exceptions.InvalidEmailException;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@Setter
public class Email {

    private static final Pattern pattern = Pattern.compile(EmailRegex.getEmailRegex());

    private String email;

    public Email(String email){
        validate(email);
        this.email = email;
    }

    private void validate(String email){
        Objects.requireNonNull(email, "O Email não pode ser nulo");
        if(!pattern.matcher(email).matches())
            throw new InvalidEmailException();
    }
}