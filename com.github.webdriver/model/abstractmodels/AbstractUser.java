package model.abstractmodels;

import model.userdata.UserAccess;

import java.sql.Date;

public abstract class AbstractUser {            // Essa classe serve como um modelo para generalizar os usuários.
                                                // É "abstract" para que não possa ser instanciada.
                                                // Um usuário tem todos esses métodos e atributos abaixo, assim como o administrador
                                                // Por isso, User É UM AbstractUser e Administrator É UM AbstractUser
                                                // Polimorfismo aplicado para melhor manutenção de código futuro.
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
