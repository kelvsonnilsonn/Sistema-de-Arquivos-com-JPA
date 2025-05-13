package com.orizon.webdriver.domain.exceptions;

public class InvalidZipCodeException extends RuntimeException {
    public InvalidZipCodeException() { super("CEP inválido foi inserido."); }
    public InvalidZipCodeException(String message) {
        super(message);
    }
}
