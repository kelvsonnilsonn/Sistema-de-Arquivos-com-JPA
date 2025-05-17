package com.orizon.webdriver.domain.exceptions;

public class UserInexistentException extends RuntimeException {
    public UserInexistentException() {super("Usuário não existe");}
    public UserInexistentException(String message) {
        super(message);
    }
}
