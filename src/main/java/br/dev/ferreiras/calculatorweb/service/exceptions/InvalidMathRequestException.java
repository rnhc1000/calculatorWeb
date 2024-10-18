package br.dev.ferreiras.calculatorweb.service.exceptions;

public class InvalidMathRequestException extends RuntimeException{
  public InvalidMathRequestException(final String message) {
    super(message);
  }
}
