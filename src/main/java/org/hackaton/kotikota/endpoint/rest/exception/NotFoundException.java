package org.hackaton.kotikota.endpoint.rest.exception;

import static org.hackaton.kotikota.endpoint.rest.exception.ApiException.ExceptionType.CLIENT_EXCEPTION;

public class NotFoundException extends ApiException {
  public NotFoundException(String message) {
    super(CLIENT_EXCEPTION, message);
  }
}
