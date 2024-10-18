package br.dev.ferreiras.calculatorweb.controller.handlers;

import br.dev.ferreiras.calculatorweb.dto.ErrorResponseDto;
import br.dev.ferreiras.calculatorweb.service.exceptions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
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

  @ExceptionHandler(value={JsonProcessingException.class})
  public ResponseEntity<ErrorResponseDto> jsonProcessingError(final JsonProcessingException exception, final WebRequest request) {
    final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
            HttpStatus.BAD_REQUEST.value(),
            ControllerExceptionHandler.JSON_DECODING_ERROR,
            Instant.now()
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorResponseDto);
  }

  @ExceptionHandler(value = {ResourceNotFoundException.class})
  public ResponseEntity<ErrorResponseDto> resourceNotFound(final ResourceNotFoundException exception, final WebRequest request) {
    final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
            HttpStatus.NOT_FOUND.value(),
            ControllerExceptionHandler.RESOURCE_NOT_FOUND,
            Instant.now()
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(errorResponseDto);
  }

  @ExceptionHandler(value = {AccessDeniedException.class})
  public ResponseEntity<ErrorResponseDto> notAuthorized(final AccessDeniedException exception, final WebRequest request) {
    final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
            HttpStatus.UNAUTHORIZED.value(),
            ControllerExceptionHandler.NOT_AUTHORIZED,
            Instant.now()
    );

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(errorResponseDto);
  }

  @ExceptionHandler(value={ForbiddenException.class})
  public ResponseEntity<ErrorResponseDto> accessForbidden(final ForbiddenException exception, final WebRequest request) {
    final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
            HttpStatus.FORBIDDEN.value(),
            ControllerExceptionHandler.ACCESS_DENIED,
            Instant.now()
    );

    return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(errorResponseDto);
  }
/*
Both the 401 and 403 error codes address user authentication but at different stages.
401 refers to the lack of valid authentication credentials, whereas the 403 error occurs after authentication,
signaling the absence of necessary permissions to access a resource.
 */
  @ExceptionHandler(value={DatabaseException.class})
  public ResponseEntity<ErrorResponseDto> database(final DatabaseException exception, final WebRequest request) {
    final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
            HttpStatus.METHOD_NOT_ALLOWED.value(),
            ControllerExceptionHandler.DATABASE_ERROR,
            Instant.now()
    );

    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED.value()).body(errorResponseDto);
  }

  @ExceptionHandler(value = {BadCredentialsException.class})
  public ResponseEntity<ErrorResponseDto> badCredentials(final BadCredentialsException exception, final WebRequest request) {
    final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
            HttpStatus.FORBIDDEN.value(),
            ControllerExceptionHandler.ACCESS_DENIED,
            Instant.now()
    );

    return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(errorResponseDto);
  }

  @ExceptionHandler(value={InvalidMathRequestException.class})
  public ResponseEntity<Object> forbidden(final InvalidMathRequestException exception, final WebRequest request) {
    final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            ControllerExceptionHandler.ILLEGAL_MATH_REQUEST,
            Instant.now()
    );

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY.value()).body(errorResponseDto);
  }

  @ExceptionHandler(value={OutOfBalanceException.class})
  public ResponseEntity<ErrorResponseDto> outOfBalance(final OutOfBalanceException exception, final WebRequest request) {
    final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
            HttpStatus.PAYMENT_REQUIRED.value(),
            ControllerExceptionHandler.NOT_ENOUGH_FUNDS,
            Instant.now()
    );

    return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(errorResponseDto);
  }

  @ExceptionHandler(value={UserAlreadyExistsException.class})
  public ResponseEntity<ErrorResponseDto> userAlreadyExists(final UserAlreadyExistsException exception, final WebRequest request) {
    final ErrorResponseDto errorResponseDto = new ErrorResponseDto(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            ControllerExceptionHandler.USER_ALREADY_EXISTS,
            Instant.now()
    );

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponseDto);
  }

}
