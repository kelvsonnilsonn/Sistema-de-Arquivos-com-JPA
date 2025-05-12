package com.orizon.webdriver.domain.model.user.userdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserAccess{

    private String login;
    private Password password;
    private Email email;

}