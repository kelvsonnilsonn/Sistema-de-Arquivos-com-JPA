package com.orizon.webdriver.domain.model.user;

import com.orizon.webdriver.domain.model.user.userdata.UserAccess;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public final class User extends AbstractUser {

    private int fkComment;

    public User(int id, UserAccess userAccess, Date date){
        super(id, userAccess, date);
    }
}