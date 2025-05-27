package com.orizon.webdriver.domain.exceptions;

public class CommentInexistentException extends RuntimeException {
    public CommentInexistentException(){super("Comentário não existe.");}
    public CommentInexistentException(String message) {
        super(message);
    }
}
