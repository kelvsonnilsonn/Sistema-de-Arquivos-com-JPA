package com.orizon.webdriver.domain.model.user;


import com.orizon.webdriver.domain.model.user.userdata.UserAccess;

import java.sql.Date;

public final class Administrator extends AbstractUser {
    public Administrator(String login, String email, String password) {
        super(login, email, password);
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