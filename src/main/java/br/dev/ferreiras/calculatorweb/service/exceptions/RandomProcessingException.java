package br.dev.ferreiras.calculatorweb.service.exceptions;

public class RandomProcessingException extends RuntimeException{

  public RandomProcessingException(final String message) {
    super(message);
  }
}
