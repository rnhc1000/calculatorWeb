package br.dev.ferreiras.calculatorWeb.controller.handlers;

import br.dev.ferreiras.calculatorWeb.dto.CustomizedErrors;
import br.dev.ferreiras.calculatorWeb.dto.ValidationError;
import br.dev.ferreiras.calculatorWeb.service.exceptions.DatabaseException;
import br.dev.ferreiras.calculatorWeb.service.exceptions.ForbiddenException;
import br.dev.ferreiras.calculatorWeb.service.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

public class ControllerExceptionHandler {

  @ExceptionHandler (ResourceNotFoundException.class)
  public ResponseEntity<CustomizedErrors> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    CustomizedErrors err = new CustomizedErrors(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<CustomizedErrors> database(DatabaseException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    CustomizedErrors err = new CustomizedErrors(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<CustomizedErrors> methodArgumentNotValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
    ValidationError err = new ValidationError(Instant.now(), status.value(), "Invalid Data!", request.getRequestURI());
    for (FieldError f : e.getBindingResult().getFieldErrors()) {
      err.addError(f.getField(), f.getDefaultMessage());
    }
    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<CustomizedErrors> forbidden(ForbiddenException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.FORBIDDEN;
    CustomizedErrors err = new CustomizedErrors(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
    return ResponseEntity.status(status).body(err);
  }

}
