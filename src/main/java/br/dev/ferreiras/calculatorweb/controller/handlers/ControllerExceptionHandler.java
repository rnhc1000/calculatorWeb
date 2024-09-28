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

  private static final String TIME_STAMP = "timestamp: ";
  private static final String MESSAGE= "message: ";
  private static final String STATUS = "status: ";
  private static final String ERRORS = "errors: ";
  private final static String EXCEPTION = "exception: ";

  @ExceptionHandler (JsonProcessingException.class)
  public ResponseEntity<Object> jsonProcessingError(JsonProcessingException exception, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIME_STAMP, Instant.now());
    body.put(MESSAGE, "Processing error when decoding Json");
    body.put(EXCEPTION, exception);
    return this.handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler (ResourceNotFoundException.class)
  public ResponseEntity<Object> resourceNotFound(ResourceNotFoundException exception, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIME_STAMP, Instant.now());
    body.put(MESSAGE, "Resource not found");
    body.put(EXCEPTION, exception);
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND );
  }

  @ExceptionHandler (AccessDeniedException.class)
  public ResponseEntity<Object> notAuthorized(ResourceNotFoundException exception, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIME_STAMP, Instant.now());
    body.put(MESSAGE, "Not Authorized");
    body.put(EXCEPTION, exception);
    return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED );
  }

  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<Object> database(DatabaseException exception, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIME_STAMP, Instant.now());
    body.put(MESSAGE, "Error accessing the database!");
    body.put(EXCEPTION, exception);
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST );
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<Object> forbidden(ForbiddenException exception, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIME_STAMP, LocalDateTime.now());
    body.put(STATUS, "Illegal Math Operation!");
    body.put(EXCEPTION, exception);

    return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY );
  }

  public ResponseEntity<Object> handleMethodArgumentNotValid(
          MethodArgumentNotValidException exception, HttpHeaders headers,
          HttpStatus status, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIME_STAMP, LocalDate.now());
    body.put(STATUS, status.value());
    body.put(EXCEPTION, exception);
    List<String> errors = exception.getBindingResult()
                            .getFieldErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .toList();

    body.put(ERRORS, errors);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

}
