package com.orizon.webdriver.domain.exceptions;

public class SupportInexistentException extends RuntimeException {
    public SupportInexistentException() { super("Suporte não existe."); }
    public SupportInexistentException(String message) {
        super(message);
    }
}
