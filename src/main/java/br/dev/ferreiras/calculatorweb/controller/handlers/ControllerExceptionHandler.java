package br.dev.ferreiras.calculatorweb.controller.handlers;

import br.dev.ferreiras.calculatorweb.dto.ErrorResponseDto;
import br.dev.ferreiras.calculatorweb.service.exceptions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.Instant;

@ControllerAdvice(annotations = RestController.class)
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String NOT_ENOUGH_FUNDS = "Not enough funds to keep doing maths!!";
  private static final String JSON_DECODING_ERROR = "Processing error when decoding Json";
  private static final String RESOURCE_NOT_FOUND = "Resource not found";
  private static final String NOT_AUTHORIZED = "Not authorized";
  private static final String ACCESS_DENIED = "Access denied";
  private static final String DATABASE_ERROR = "The system is suffering some issues.! Try again later!";
  private static final String ILLEGAL_MATH_REQUEST = "Illegal math operation!";
  private static final String USER_ALREADY_EXISTS = "User already exists!";
  private static final String USER_NOT_FOUND = "User not found!";

  @ExceptionHandler(value = {JsonProcessingException.class})
  public ResponseEntity<ErrorResponseDto> jsonProcessingError(final JsonProcessingException exception, final HttpServletRequest request) {
    final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
        Instant.now(),
        HttpStatus.BAD_REQUEST.value(),
        exception.getMessage(),
        request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorResponseDto);
  }

  @ExceptionHandler(value = {ResourceNotFoundException.class})
  public ResponseEntity<ErrorResponseDto> resourceNotFound(final ResourceNotFoundException exception, final HttpServletRequest request) {
    final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
        Instant.now(),
        HttpStatus.NOT_FOUND.value(),
        exception.getMessage(),
        request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(errorResponseDto);
  }

  @ExceptionHandler(value = {AccessDeniedException.class})
  public ResponseEntity<ErrorResponseDto> notAuthorized(final AccessDeniedException exception, final HttpServletRequest request) {
    final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
        Instant.now(),
        HttpStatus.UNAUTHORIZED.value(),
        exception.getMessage(),
        request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(errorResponseDto);
  }

  @ExceptionHandler(value = {ForbiddenException.class})
  public ResponseEntity<ErrorResponseDto> accessForbidden(final ForbiddenException exception, final HttpServletRequest request) {
    final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
        Instant.now(),
        HttpStatus.FORBIDDEN.value(),
        exception.getMessage(),
        request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(errorResponseDto);
  }

  /*
  Both the 401 and 403 error codes address user authentication but at different stages.
  401 refers to the lack of valid authentication credentials, whereas the 403 error occurs after authentication,
  signaling the absence of necessary permissions to access a resource.
   */
  @ExceptionHandler(value = {DatabaseException.class})
  public ResponseEntity<ErrorResponseDto> database(final DatabaseException exception, final HttpServletRequest request) {
    final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
        Instant.now(),
        HttpStatus.METHOD_NOT_ALLOWED.value(),
        exception.getMessage(),
        request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED.value()).body(errorResponseDto);
  }

  @ExceptionHandler(value = {BadCredentialsException.class})
  public ResponseEntity<ErrorResponseDto> badCredentials(final BadCredentialsException exception, final HttpServletRequest request) {
    final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
        Instant.now(),
        HttpStatus.FORBIDDEN.value(),
        exception.getMessage(),
        request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(errorResponseDto);
  }

  @ExceptionHandler(value = {InvalidMathRequestException.class})
  public ResponseEntity<Object> forbidden(final InvalidMathRequestException exception, final HttpServletRequest request) {
    final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
        Instant.now(),
        HttpStatus.UNPROCESSABLE_ENTITY.value(),
        exception.getMessage(),
        request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY.value()).body(errorResponseDto);
  }

  @ExceptionHandler(value = {OutOfBalanceException.class})
  public ResponseEntity<ErrorResponseDto> outOfBalance(final OutOfBalanceException exception, final HttpServletRequest request) {
    final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
        Instant.now(),
        HttpStatus.PAYMENT_REQUIRED.value(),
        exception.getMessage(),
        request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(errorResponseDto);
  }

  @ExceptionHandler(value = {UserAlreadyExistsException.class})
  public ResponseEntity<ErrorResponseDto> userAlreadyExists(final UserAlreadyExistsException exception, final HttpServletRequest request) {
    final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
        Instant.now(),
        HttpStatus.UNPROCESSABLE_ENTITY.value(),
        exception.getMessage(),
        request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponseDto);
  }

  @ExceptionHandler(value = {UsernameNotFoundException.class})
  public ResponseEntity<ErrorResponseDto> userNotFound(final UsernameNotFoundException exception, final HttpServletRequest request) {
    final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
        Instant.now(),
        HttpStatus.UNPROCESSABLE_ENTITY.value(),
        exception.getMessage(),
        request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponseDto);
  }
}
