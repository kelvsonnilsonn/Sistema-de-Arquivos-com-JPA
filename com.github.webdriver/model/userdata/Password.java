package model.userdata;

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
            throw new IllegalArgumentException("Password cannot be less than 3 characters.");
        Objects.requireNonNull(pass, "Password cannot be null");
    }
}
