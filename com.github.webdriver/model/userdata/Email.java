package model.userdata;

import model.dataregex.EmailRegex;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {

    private static final Pattern pattern = Pattern.compile(EmailRegex.getEmailRegex());

    private String email;

    public Email(String email){
        validate(email);
        this.email = email;
    }

    private void validate(String email){
        Objects.requireNonNull(email, "Email cannot be null.");
        if(!pattern.matcher(email).matches())
            throw new IllegalArgumentException("An invalid email was used.");
    }

    public String getUserEmail(){ return this.email; }
}