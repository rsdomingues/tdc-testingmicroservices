package com.tdcsample.checkout.gateway.http;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.tdcsample.checkout.conf.log.LogKey;
import com.tdcsample.checkout.domain.exception.FeatureException;
import com.tdcsample.checkout.domain.exception.IntegrationException;
import com.tdcsample.checkout.domain.exception.NotPaidException;
import com.tdcsample.checkout.domain.exception.ValidationException;
import com.tdcsample.checkout.gateway.http.jsons.errorhandling.ErrorField;
import com.tdcsample.checkout.gateway.http.jsons.errorhandling.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.logstash.logback.argument.StructuredArguments.value;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse processValidationError(MethodArgumentNotValidException e) {
    log.warn("An validation error occured: {}", e);
    final BindingResult result = e.getBindingResult();
    final List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
    final ErrorResponse errorResponse = new ErrorResponse(ErrorResponse.ERR_VALIDATION);

    Map<String, List<String>> fieldsMap = new HashMap<>();
    fieldErrors.forEach(
        fieldError ->
            fieldsMap
                .computeIfAbsent(fieldError.getCode(), fields -> new ArrayList<>())
                .add(fieldError.getField()));

    List<ErrorField> errors = new ArrayList<>();
    fieldsMap.forEach(
        (message, fields) ->
            errors.add(ErrorField.builder().messageCode(message).fields(fields).build()));

    errorResponse.setErrorFields(errors);
    return errorResponse;
  }

  @ExceptionHandler(IntegrationException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  @ResponseBody
  public ErrorResponse processFeatureException(IntegrationException e) {
    log.warn("Occurred integration error: {}", e.getMessage());
    return new ErrorResponse(e.getName(), e.getDescription());
  }

  @ExceptionHandler(FeatureException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ResponseBody
  public ErrorResponse processFeatureException(FeatureException e) {
    log.warn("Occurred error for feature: {}", value(LogKey.FEATURE_KEY.toString(), e.getName()));
    final String message = "Feature disabled: " + e.getName();
    return new ErrorResponse(ErrorResponse.ERR_FEATURE, message);
  }

  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  @ResponseBody
  public ErrorResponse processValidationException(ValidationException e) {
    log.warn("Validation error occurred: {}", e.getMessage(), e);
    return new ErrorResponse(ErrorResponse.ERR_UNPROCESSABLE_ENTITY, e.getMessage());
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ResponseBody
  public ErrorResponse processMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {
    log.warn("Method not allowed error occurred: {}", e.getMessage(), e);
    return new ErrorResponse(ErrorResponse.ERR_METHOD_NOT_SUPPORTED, e.getMessage());
  }

  @ExceptionHandler(HystrixRuntimeException.class)
  public ResponseEntity<ErrorResponse> processHystrixException(HystrixRuntimeException e) {

    log.warn("A Hystrix Runtime Exception occurred: {}", e.getMessage());

    ResponseEntity.BodyBuilder builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
    ErrorResponse errorResponse =
        new ErrorResponse(ErrorResponse.ERR_INTERNAL_SERVER_ERROR, "Internal server error");

    return builder.body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ErrorResponse processException(Exception e) {
    log.error("An unexpected error occured: {}", value(LogKey.CAUSE.toString(), e.getMessage()), e);
    return new ErrorResponse(ErrorResponse.ERR_INTERNAL_SERVER_ERROR, "Internal server error");
  }

  @ExceptionHandler(NotPaidException.class)
  public ResponseEntity<ErrorResponse> unprocessableCheckout(NotPaidException e) {

    log.warn("A unprocessable checkout error: {}", e.getMessage());

    ResponseEntity.BodyBuilder builder = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY);

    ErrorResponse errorResponse =
            new ErrorResponse(ErrorResponse.ERR_PAYMENT_NOT_APPROVED, "Error processing order checkout");

    return builder.body(errorResponse);
  }
}
