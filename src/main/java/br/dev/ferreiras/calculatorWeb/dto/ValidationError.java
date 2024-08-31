package br.dev.ferreiras.calculatorWeb.dto;

import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends CustomizedErrors {

  private final List<FieldMessage> errors = new ArrayList<>();

  public ValidationError(Instant timestamp, Integer status, String error, String path) {
    super(timestamp, status, error, path);
  }

  public void addError(String fieldName, String message) {
    errors.removeIf(x -> x.fieldName().equals(fieldName));
    errors.add(new FieldMessage(fieldName, message));
  }
}
