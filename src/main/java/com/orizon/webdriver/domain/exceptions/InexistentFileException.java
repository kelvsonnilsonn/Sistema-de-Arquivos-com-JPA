package com.orizon.webdriver.domain.exceptions;

public class InexistentFileException extends RuntimeException {
    public InexistentFileException() { super("Arquivo inexistente."); }
    public InexistentFileException(String message) {
        super(message);
    }
}
