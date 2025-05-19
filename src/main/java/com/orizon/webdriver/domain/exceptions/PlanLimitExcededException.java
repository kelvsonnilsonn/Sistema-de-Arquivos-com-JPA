package com.orizon.webdriver.domain.exceptions;

public class PlanLimitExcededException extends RuntimeException {
    public PlanLimitExcededException() {super("O plano atingiu o limite máximo.");}
    public PlanLimitExcededException(String message) {
        super(message);
    }
}
