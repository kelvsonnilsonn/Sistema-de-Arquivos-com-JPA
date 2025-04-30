package model.AbstractModels;

import model.UserDataModel.UserAccess;

import java.sql.Date;

public abstract class AbstractUser {
    private final int id_admin;
    private UserAccess userAccess;
    private Date createdUserDate;

    public AbstractUser(int id, UserAccess userAccess, Date date){
        this.id_admin = id;
        this.userAccess = userAccess;
        this.createdUserDate = date;
    }

    public int getUserId() { return this.id_admin; }
    public String getUserLogin() { return this.userAccess.getUserLogin(); }
    public String getUserPassword() { return this.userAccess.getUserPassword(); }
    public String getUserEmail() { return this.userAccess.getUserEmail();}
    public String getCreatedUserDate() { return this.createdUserDate.toString(); }
}
