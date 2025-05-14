package com.orizon.webdriver.domain.valueobjects;

import com.orizon.webdriver.domain.exceptions.InvalidEmailException;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@Setter
public class Email {

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

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