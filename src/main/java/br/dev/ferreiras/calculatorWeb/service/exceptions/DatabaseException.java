package br.dev.ferreiras.calculatorWeb.service.exceptions;

public class DatabaseException extends RuntimeException{
  public DatabaseException(String message) {
    super(message);
  }
}
