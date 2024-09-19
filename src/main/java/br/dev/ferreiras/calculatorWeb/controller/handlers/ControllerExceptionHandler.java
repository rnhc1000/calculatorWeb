package br.dev.ferreiras.calculatorWeb.controller.handlers;

import br.dev.ferreiras.calculatorWeb.service.exceptions.DatabaseException;
import br.dev.ferreiras.calculatorWeb.service.exceptions.ForbiddenException;
import br.dev.ferreiras.calculatorWeb.service.exceptions.ResourceNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.channels.AcceptPendingException;
import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler (ResourceNotFoundException.class)
  public ResponseEntity<Object> resourceNotFound(ResourceNotFoundException e, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp: ", Instant.now());
    body.put("message", "Resource not found");
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND );
  }

  @ExceptionHandler (AccessDeniedException.class)
  public ResponseEntity<Object> notAuthorized(ResourceNotFoundException e, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp: ", Instant.now());
    body.put("message", "Not Authorized");
    return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED );
  }

  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<Object> database(DatabaseException e, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp: ", Instant.now());
    body.put("message", "Error accessing the database!");
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST );
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<Object> forbidden(ForbiddenException ex, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp: ", LocalDateTime.now());
    body.put("status", "Illegal Math Operation!");
    return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY );
  }

  public ResponseEntity<Object> handleMethodArgumentNotValid(
          MethodArgumentNotValidException ex, HttpHeaders headers,
          HttpStatus status, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDate.now());
    body.put("status", status.value());

    List<String> errors = ex.getBindingResult()
                            .getFieldErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .collect(Collectors.toList());

    body.put("errors", errors);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

}
