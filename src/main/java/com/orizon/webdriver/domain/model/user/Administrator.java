package com.orizon.webdriver.domain.model.user;


import com.orizon.webdriver.domain.model.user.userdata.UserAccess;

import java.sql.Date;

public final class Administrator extends AbstractUser {
    public Administrator(int id, UserAccess userAccess, Date date){
        super(id, userAccess, date);
    }
}