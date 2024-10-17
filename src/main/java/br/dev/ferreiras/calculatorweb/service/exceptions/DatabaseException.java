package br.dev.ferreiras.calculatorweb.service.exceptions;

public class DatabaseException extends RuntimeException{
  public DatabaseException(final String message) {
    super(message);
  }
}
