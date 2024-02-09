package org.hackaton.kotikota.endpoint.rest.exception;

import static org.hackaton.kotikota.endpoint.rest.exception.ApiException.ExceptionType.CLIENT_EXCEPTION;

public class BadRequestException extends ApiException {
  public BadRequestException(String message) {
    super(CLIENT_EXCEPTION, message);
  }
}
