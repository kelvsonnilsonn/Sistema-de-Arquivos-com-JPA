package com.orizon.webdriver.domain.exceptions;

public class PlanInvalidLimitException extends RuntimeException {
    public PlanInvalidLimitException(){ super("Limite de plano deve ser maior que 0.");}
    public PlanInvalidLimitException(String message) {
        super(message);
    }
}
