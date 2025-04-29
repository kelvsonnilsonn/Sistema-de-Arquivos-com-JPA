package model.UserDataModel;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {

    private static final Pattern pattern = Pattern.compile(EmailRegex.getEmailRegex());

    private void validate(String email){
        Objects.requireNonNull(email, "[ERRO] E-mail não deve ser null.");
        if(!pattern.matcher(email).matches())
            throw new IllegalArgumentException("[ERRO] O e-mail inserido não é válido.");
    }

    private String email;

    public Email(String email){
        validate(email);
        this.email = email;
    }

    public String getUserEmail(){ return this.email; }
}