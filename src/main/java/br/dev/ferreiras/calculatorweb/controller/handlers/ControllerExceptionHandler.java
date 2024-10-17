package br.dev.ferreiras.calculatorweb.controller.handlers;

import br.dev.ferreiras.calculatorweb.dto.InvalidMathRequestException;
import br.dev.ferreiras.calculatorweb.service.exceptions.DatabaseException;
import br.dev.ferreiras.calculatorweb.service.exceptions.ForbiddenException;
import br.dev.ferreiras.calculatorweb.service.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
  private static final String EXCEPTION = "exception: ";

  @ExceptionHandler (JsonProcessingException.class)
  public ResponseEntity<Object> jsonProcessingError(final JsonProcessingException exception, final WebRequest request) {
    final Map<String, Object> body = new LinkedHashMap<>();
    body.put(ControllerExceptionHandler.TIME_STAMP, Instant.now());
    body.put(ControllerExceptionHandler.MESSAGE, "Processing error when decoding Json");
    body.put(ControllerExceptionHandler.EXCEPTION, exception);
    return this.handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler (ResourceNotFoundException.class)
  public ResponseEntity<Object> resourceNotFound(final ResourceNotFoundException exception, final WebRequest request) {
    final Map<String, Object> body = new LinkedHashMap<>();
    body.put(ControllerExceptionHandler.TIME_STAMP, Instant.now());
    body.put(ControllerExceptionHandler.MESSAGE, "Resource not found");
    body.put(ControllerExceptionHandler.EXCEPTION, exception);
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND );
  }

  @ExceptionHandler (AccessDeniedException.class)
  public ResponseEntity<Object> notAuthorized(final AccessDeniedException exception, final WebRequest request) {
    final Map<String, Object> body = new LinkedHashMap<>();
    body.put(ControllerExceptionHandler.TIME_STAMP, Instant.now());
    body.put(ControllerExceptionHandler.MESSAGE, "Not Authorized");
    body.put(ControllerExceptionHandler.EXCEPTION, exception);
    return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED );
  }

  @ExceptionHandler (ForbiddenException.class)
  public ResponseEntity<Object> accessForbidden(final ForbiddenException exception, final WebRequest request) {
    final Map<String, Object> body = new LinkedHashMap<>();
    body.put(ControllerExceptionHandler.TIME_STAMP, Instant.now());
    body.put(ControllerExceptionHandler.MESSAGE, "Access Denied");
    body.put(ControllerExceptionHandler.EXCEPTION, exception);
    return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
  }
/*
Both the 401 and 403 error codes address user authentication but at different stages.
401 refers to the lack of valid authentication credentials, whereas the 403 error occurs after authentication,
signaling the absence of necessary permissions to access a resource.
 */
  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<Object> database(final DatabaseException exception, final WebRequest request) {
    final Map<String, Object> body = new LinkedHashMap<>();
    body.put(ControllerExceptionHandler.TIME_STAMP, Instant.now());
    body.put(ControllerExceptionHandler.MESSAGE, "Error accessing the database!");
    body.put(ControllerExceptionHandler.EXCEPTION, exception);
    return new ResponseEntity<>(body, HttpStatus.METHOD_NOT_ALLOWED );
  }

  @ExceptionHandler (BadCredentialsException.class)
  public ResponseEntity<Object> badCredentials(final BadCredentialsException exception, final WebRequest request) {
    final Map<String, Object> body = new LinkedHashMap<>();
    body.put(ControllerExceptionHandler.TIME_STAMP, Instant.now());
    body.put(ControllerExceptionHandler.MESSAGE, "Access Denied");
    body.put(ControllerExceptionHandler.EXCEPTION, exception);
    return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(InvalidMathRequestException.class)
  public ResponseEntity<Object> forbidden(final InvalidMathRequestException exception, final WebRequest request) {
    final Map<String, Object> body = new LinkedHashMap<>();
    body.put(ControllerExceptionHandler.TIME_STAMP, LocalDateTime.now());
    body.put(ControllerExceptionHandler.STATUS, "Illegal Math Operation!");
    body.put(ControllerExceptionHandler.EXCEPTION, exception);

    return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY );
  }

  public ResponseEntity<Object> handleMethodArgumentNotValid(
          final MethodArgumentNotValidException exception, final HttpHeaders headers,
          final HttpStatus status, final WebRequest request) {

    final Map<String, Object> body = new LinkedHashMap<>();
    body.put(ControllerExceptionHandler.TIME_STAMP, LocalDate.now());
    body.put(ControllerExceptionHandler.STATUS, status.value());
    body.put(ControllerExceptionHandler.EXCEPTION, exception);
    final List<String> errors = exception.getBindingResult()
                                         .getFieldErrors()
                                         .stream()
                                         .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                         .toList();

    body.put(ControllerExceptionHandler.ERRORS, errors);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

}
