package br.dev.ferreiras.calculatorweb.service.exceptions;

  public class ForbiddenException extends RuntimeException {
    public ForbiddenException(final String message) {
      super(message);
    }
}
