package br.dev.ferreiras.calculatorWeb.service.exceptions;

  public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {

      super(message);
    }
}
