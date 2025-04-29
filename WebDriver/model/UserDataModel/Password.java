package model.UserDataModel;

import java.util.Objects;

public class Password{
    private String pass;

    public Password(String pass){
        validate(pass);
        this.pass = pass;
    }

    public String getUserPassword() { return this.pass; }

    private void validate(String pass){
        if(pass.length() < 3)
            throw new IllegalArgumentException("[ERRO] O e-mail inserido não é válido.");
        Objects.requireNonNull(pass, "Password can't be less than 3 caracters.");
    }
}
