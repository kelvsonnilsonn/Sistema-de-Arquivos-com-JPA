package com.orizon.webdriver.domain.valueobjects;


import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Construtor sem args para Hibernate
public class Email {
    private static final String EMAIL_REGEX =
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    private String email;

    public Email(String email) {
        validate(email);
        this.email = email;
    }

    public static Email of(String email) {
        return new Email(email);
    }

    private void validate(String email) {
        if (email == null || !pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Email inválido: " + email);
        }
    }

}