package br.dev.ferreiras.calculatorweb.service.exceptions;

public class OutOfBalanceException extends RuntimeException{
  public OutOfBalanceException(final String message) {
    super(message);
  }
}
