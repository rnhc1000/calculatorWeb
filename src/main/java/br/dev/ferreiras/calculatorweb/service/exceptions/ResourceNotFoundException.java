package br.dev.ferreiras.calculatorweb.service.exceptions;

public class ResourceNotFoundException extends RuntimeException{
  public ResourceNotFoundException(String message) {

    super(message);
  }
}
