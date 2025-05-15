package com.orizon.webdriver.domain.exceptions;

public class InstitutionLimitException extends RuntimeException {
    public InstitutionLimitException() { super("Limite de instituições atingido (1).");}
    public InstitutionLimitException(String message) {
        super(message);
    }
}
