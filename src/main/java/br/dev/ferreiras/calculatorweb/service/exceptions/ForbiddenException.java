package br.dev.ferreiras.calculatorweb.service.exceptions;

  public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {

      super(message);
    }
}
