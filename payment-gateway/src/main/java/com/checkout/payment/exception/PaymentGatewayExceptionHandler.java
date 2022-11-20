package com.checkout.payment.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException.UnprocessableEntity;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class PaymentGatewayExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
    var body = new HashMap<>();
    body.put("message", ex.getMessage());
    body.put("timestamp",new Date());
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex, WebRequest request) {
    var body = new HashMap<>();
    body.put("message", ex.getMessage());
    body.put("timestamp",new Date());
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
    var body = new HashMap<>();
    var errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.toList());
    body.put("timestamp",new Date());
    body.put("errors", errors);
    body.put("message", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidRequestException.class)
  public ResponseEntity<Object> invalidRequestException(InvalidRequestException ex, WebRequest request) {
    var body = new HashMap<>();
    body.put("message", ex.getMessage());
    body.put("timestamp",new Date());
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> exceptionHandler(Exception ex, WebRequest request) {
    var body = new HashMap<>();
    body.put("message", ex.getMessage());
    body.put("timestamp",new Date());
    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
