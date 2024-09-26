package br.dev.ferreiras.calculatorweb.controller.handlers;

import br.dev.ferreiras.calculatorweb.service.exceptions.DatabaseException;
import br.dev.ferreiras.calculatorweb.service.exceptions.ForbiddenException;
import br.dev.ferreiras.calculatorweb.service.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

  private final static String TIME_STAMP = "timestamp: ";
  private final static String MESSAGE= "message: ";
  private final static String STATUS = "status: ";
  private final static String ERRORS = "errors: ";
  private final static String EXCEPTION = "exception: ";

  @ExceptionHandler (JsonProcessingException.class)
  public ResponseEntity<Object> jsonProcessingError(JsonProcessingException ex, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIME_STAMP, Instant.now());
    body.put(MESSAGE, "Processing error when decoding Json");
    body.put(EXCEPTION, ex);
    return this.handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler (ResourceNotFoundException.class)
  public ResponseEntity<Object> resourceNotFound(ResourceNotFoundException e, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIME_STAMP, Instant.now());
    body.put(MESSAGE, "Resource not found");
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND );
  }

  @ExceptionHandler (AccessDeniedException.class)
  public ResponseEntity<Object> notAuthorized(ResourceNotFoundException e, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIME_STAMP, Instant.now());
    body.put(MESSAGE, "Not Authorized");
    return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED );
  }

  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<Object> database(DatabaseException e, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIME_STAMP, Instant.now());
    body.put(MESSAGE, "Error accessing the database!");
    body.put(EXCEPTION, e);
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST );
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<Object> forbidden(ForbiddenException ex, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIME_STAMP, LocalDateTime.now());
    body.put(STATUS, "Illegal Math Operation!");
    return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY );
  }

  public ResponseEntity<Object> handleMethodArgumentNotValid(
          MethodArgumentNotValidException ex, HttpHeaders headers,
          HttpStatus status, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIME_STAMP, LocalDate.now());
    body.put(STATUS, status.value());

    List<String> errors = ex.getBindingResult()
                            .getFieldErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .toList();

    body.put(ERRORS, errors);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

}
