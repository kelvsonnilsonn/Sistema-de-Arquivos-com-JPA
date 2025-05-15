package com.orizon.webdriver.domain.exceptions;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() { super("Email inválido foi inserido."); }
    public InvalidEmailException(String message) {
        super(message);
    }
}
