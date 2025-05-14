package com.orizon.webdriver.domain.exceptions;

public class SupportException extends RuntimeException {
    public SupportException() { super("Não foi possível encontrar o pedido de suporte."); }
    public SupportException(String message) {
        super(message);
    }
}
