package com.orizon.webdriver.domain.model.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public final class Administrator extends AbstractUser {

    private long id_administrador;

    public Administrator(String login, String email, String password) {
        super(login, email, password);
    }
}