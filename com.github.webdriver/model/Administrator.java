package model;

import model.abstractmodels.AbstractUser;
import model.userdata.UserAccess;

import java.sql.Date;

public class Administrator extends AbstractUser {
    public Administrator(int id, UserAccess userAccess, Date date){
        super(id, userAccess, date);
    }


}