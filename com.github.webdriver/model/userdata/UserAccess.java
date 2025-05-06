package model.userdata;

public class UserAccess{
    private String login;
    private Password password;
    private Email email;

    public UserAccess(String login, Password password, model.userdata.Email email){
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public String getUserLogin() { return this.login; }
    public String getUserPassword() { return this.password.getUserPassword(); }
    public String getUserEmail() { return this.email.getUserEmail(); }
}