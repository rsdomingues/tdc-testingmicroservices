package com.tdcsample.checkout.domain.exception;

import lombok.Getter;

@Getter
public class TimeoutException extends RuntimeException {

  private static final long serialVersionUID = -2847258260604084133L;

  private final String message;

  public TimeoutException(String message) {
    super(message);
    this.message = message;
  }
}
