package model;

import java.sql.Date;

import model.abstractmodels.AbstractUser;
import model.userdata.UserAccess;

public class User extends AbstractUser {
    public User(int id, UserAccess userAccess, Date date){
        super(id, userAccess, date);
    }
}