package com.orizon.webdriver.domain.model.user;

import com.orizon.webdriver.domain.model.user.userdata.UserAccess;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public final class User extends AbstractUser {

    public User(String login, String email, String senha){
        super(login, email, senha);
    }

    @Override
    public void load() {

    }

    @Override
    public void edit() {

    }

    @Override
    public void delete() {

    }
}