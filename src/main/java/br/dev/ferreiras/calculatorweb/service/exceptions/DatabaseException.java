package br.dev.ferreiras.calculatorweb.service.exceptions;

public class DatabaseException extends RuntimeException{
  public DatabaseException(String message) {
    super(message);
  }
}
