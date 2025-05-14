package com.orizon.webdriver.domain.exceptions;

public class DupliquedFileException extends RuntimeException {
    public DupliquedFileException() { super("Houve tentativa de inserir arquivo duplicado."); }
    public DupliquedFileException(String message) {
        super(message);
    }
}
