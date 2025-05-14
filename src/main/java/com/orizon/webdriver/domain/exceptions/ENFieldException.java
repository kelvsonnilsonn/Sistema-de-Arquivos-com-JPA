package com.orizon.webdriver.domain.exceptions;

public class ENFieldException extends RuntimeException {
    public ENFieldException() { super("Foi inserido campos nulos.");}
    public ENFieldException(Object o) { super("Foi inserido campos nulos." + o);}
    public ENFieldException(String message) {
        super(message);
    }
}
