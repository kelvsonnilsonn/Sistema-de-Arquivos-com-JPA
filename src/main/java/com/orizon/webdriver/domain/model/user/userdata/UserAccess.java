package com.orizon.webdriver.domain.model.user.userdata;

import com.orizon.webdriver.domain.exceptions.EmptyLoginFieldException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class UserAccess{

    private String login;
    private Password password;
    private Email email;

    public UserAccess(String login, String email, String password){
        this.login = Objects.requireNonNull(login, () -> {throw new EmptyLoginFieldException();});
        this.password = new Password(password);
        this.email = new Email(email);
    }
}