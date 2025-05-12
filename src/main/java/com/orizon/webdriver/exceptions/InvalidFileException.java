package com.orizon.webdriver.exceptions;

public class InvalidFileException extends RuntimeException {
  public InvalidFileException() { super("O Arquivo inserido é inválido."); }
  public InvalidFileException(String message) {
    super(message);
  }
}
