package com.checkout.payment.exception;

public class InvalidRequestException extends RuntimeException {

  private static final long serialVersionUID = -435742587845152856L;

  public InvalidRequestException(String message) {
    super(message);
  }
}
