package com.tdcsample.checkout.conf.feign;

import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.nio.charset.Charset;

public class SpringWebClientErrorDecoder implements ErrorDecoder {

  private ErrorDecoder delegate = new Default();

  @Override
  public Exception decode(final String methodKey, final Response response) {
    HttpStatus statusCode = HttpStatus.valueOf(response.status());
    Charset charset = Charset.forName("UTF-8");

    if (statusCode.is4xxClientError()) {
      return new HttpClientErrorException(
          statusCode,
          response.reason(),
          responseHeaders(response),
          responseBody(response),
          charset);
    }

    if (statusCode.is5xxServerError()) {
      return new HttpServerErrorException(
          statusCode,
          response.reason(),
          responseHeaders(response),
          responseBody(response),
          charset);
    }

    return delegate.decode(methodKey, response);
  }

  private HttpHeaders responseHeaders(final Response response) {
    HttpHeaders headers = new HttpHeaders();
    response
        .headers()
        .entrySet()
        .stream()
        .forEach(entry -> headers.put(entry.getKey(), Lists.newArrayList(entry.getValue())));
    return headers;
  }

  private byte[] responseBody(final Response response) {
    try {
      return ByteStreams.toByteArray(response.body().asInputStream());
    } catch (IOException e) {
      throw new HttpMessageNotReadableException("Failed to process response body.", e);
    }
  }
}
