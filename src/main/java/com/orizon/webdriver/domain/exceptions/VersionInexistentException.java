package com.orizon.webdriver.domain.exceptions;

public class VersionInexistentException extends RuntimeException {
    public VersionInexistentException() {super("Versão inexistente.");}
    public VersionInexistentException(String message) {
        super(message);
    }
}
