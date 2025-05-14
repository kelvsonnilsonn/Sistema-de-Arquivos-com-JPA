package com.orizon.webdriver.domain.model.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class User extends AbstractUser {

    public User(String login, String email, String senha){
        super(login, email, senha);
    }
}