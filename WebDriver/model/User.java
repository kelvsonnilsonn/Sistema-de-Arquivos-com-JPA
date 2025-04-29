package model;

import java.sql.Date;

import UserDataModel.Password;
import UserDataModel.UserAccess;
import UserDataModel.Email.Email;

public class User {
    private final int id;
    private UserACcess userAccess;
    private Date createdUserDate;

    public User(int id, UserAccess userAccess, Date date){
        this.id = id;
        this.userAccess = userAccess;
        this.createdUserDate = date;
    }

    public int getUserId() { return this.id; }
    public String getUserLogin() { return this.userAccess.getUserLogin(); }
    public String getUserPassword() { return this.userAccess.getUserPassword(); }
    public String getUserEmail() { return this.userAccess.getUserEmail();}
    public String getCreatedUserDate() { return this.createdUserDate.toString(); }
}