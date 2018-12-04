package com.tdcsample.checkout.domain.exception;

import com.tdcsample.checkout.gateway.http.jsons.ErrorField;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends RuntimeException {

  private static final long serialVersionUID = 6858187853503227198L;

  @Getter private final transient List<ErrorField> errorFields = new ArrayList<>();

  public ValidationException(String message) {
    super(message);
  }

  public ValidationException(String message, List<ErrorField> errorFields) {
    super(message);
    this.errorFields.addAll(errorFields);
  }
}
