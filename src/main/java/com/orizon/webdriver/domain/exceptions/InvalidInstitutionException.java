package com.orizon.webdriver.domain.exceptions;

public class InvalidInstitutionException extends RuntimeException {
    public InvalidInstitutionException() { super("Uma instituição inválida foi buscada."); }
    public InvalidInstitutionException(String message) {
        super(message);
    }
}
