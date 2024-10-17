package br.dev.ferreiras.calculatorweb.dto;

public class InvalidMathRequestException extends RuntimeException{
  public InvalidMathRequestException() {
    super(
            "Please, use valid math operators!"
    );
  }
}
