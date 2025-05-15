package com.orizon.webdriver.domain.exceptions;

public class InvalidFileTypeException extends RuntimeException {
    public InvalidFileTypeException() { super("O tipo de arquivo é inválido."); }
    public InvalidFileTypeException(String message) {
        super(message);
    }
}
