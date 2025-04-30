package model;

import model.AbstractModels.AbstractUser;
import model.UserDataModel.UserAccess;

import java.sql.Date;

public class Administrator extends AbstractUser {
    public Administrator(int id, UserAccess userAccess, Date date){
        super(id, userAccess, date);
    }
}