package br.dev.ferreiras.calculatorweb.service.exceptions;

public class ResourceNotFoundException extends RuntimeException{
  public ResourceNotFoundException(final String message) {
    super(message);
  }
}
