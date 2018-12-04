package com.tdcsample.checkout.domain.exception;

import lombok.Getter;

@Getter
public class IntegrationException extends RuntimeException {

  private static final long serialVersionUID = 8540165710126634860L;

  private final String name;
  private final String description;

  public IntegrationException(String name, String description, String message) {
    super(message);
    this.name = name;
    this.description = description;
  }
}
