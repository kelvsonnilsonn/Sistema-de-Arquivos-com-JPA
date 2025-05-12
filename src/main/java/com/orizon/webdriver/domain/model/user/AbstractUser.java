package com.orizon.webdriver.domain.model.user;

import com.orizon.webdriver.domain.model.user.userdata.UserAccess;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@Getter
@Setter
public sealed abstract class AbstractUser permits Administrator, User {

    private final int id;
    @Autowired
    private UserAccess userAccess;
    private Date createdUserDate;

}
