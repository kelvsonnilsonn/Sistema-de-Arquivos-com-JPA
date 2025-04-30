package model;

import java.sql.Date;

import model.AbstractModels.AbstractUser;
import model.UserDataModel.UserAccess;

public class User extends AbstractUser {
    public User(int id, UserAccess userAccess, Date date){
        super(id, userAccess, date);
    }
}