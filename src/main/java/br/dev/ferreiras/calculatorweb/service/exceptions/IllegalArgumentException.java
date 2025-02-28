package br.dev.ferreiras.calculatorweb.service.exceptions;

public class IllegalArgumentException extends RuntimeException{

  public IllegalArgumentException(final String message) {
    super(message);
  }
}
