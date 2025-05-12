package com.orizon.webdriver.exceptions;

public class ShortPasswordException extends RuntimeException {
    public ShortPasswordException() { super("A senha não pode ser menor que 3 caracteres."); }
    public ShortPasswordException(String message) {
        super(message);
    }
}
