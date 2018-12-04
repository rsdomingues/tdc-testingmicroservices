package com.tdcsample.checkout.gateway.http;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.tdcsample.checkout.domain.exception.FeatureException;
import com.tdcsample.checkout.domain.exception.IntegrationException;
import com.tdcsample.checkout.domain.exception.ValidationException;
import com.tdcsample.checkout.gateway.http.jsons.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class CustomExceptionHandlerTestController {

  @GetMapping("/test/method-not-supported")
  public void methodNotSupported() {}

  @GetMapping("/test/internal-server-error")
  public void internalServerError() {
    throw new RuntimeException();
  }

  @GetMapping("/test/unprocessable-entity-error")
  public void unprocessableEntityError() {
    throw new ValidationException("");
  }

  @GetMapping("/test/integration-airport-error")
  public void integrationAirportError() {
    throw new IntegrationException(ErrorResponse.ERR_INTEGRATION_AIRPORT_ERROR, "", "");
  }

  @GetMapping("/test/fallback-generic-error")
  public void fallbackGenericError() {
    throw new HystrixRuntimeException(
        HystrixRuntimeException.FailureType.BAD_REQUEST_EXCEPTION,
        null,
        null,
        null,
        new Exception("", new Exception("", new RuntimeException("Generic Exception"))));
  }

  @GetMapping("/test/feature-disabled")
  public void featureDisabled() {
    throw new FeatureException(null, "test", null);
  }

  @PostMapping("/test/method-argument")
  public void methodArgument(@Valid @RequestBody TestDTO testDTO) {}

  public static class TestDTO {

    @NotNull private String test;

    public String getTest() {
      return test;
    }

    public void setTest(String test) {
      this.test = test;
    }
  }
}
