package com.orizon.webdriver.domain.exceptions;

public class EmptyLoginFieldException extends RuntimeException {
    public EmptyLoginFieldException() { super("O campo de login não pode ser nulo");}
    public EmptyLoginFieldException(String message) {
        super(message);
    }
}
