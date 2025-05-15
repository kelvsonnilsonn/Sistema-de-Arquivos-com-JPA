package com.orizon.webdriver.domain.exceptions;

public class InvalidPlanException extends RuntimeException {
    public InvalidPlanException() { super("O plano inserido é inválido."); }
    public InvalidPlanException(String message) {
        super(message);
    }
}
