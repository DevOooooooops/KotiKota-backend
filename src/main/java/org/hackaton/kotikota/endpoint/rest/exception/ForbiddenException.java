package org.hackaton.kotikota.endpoint.rest.exception;

import static org.hackaton.kotikota.endpoint.rest.exception.ApiException.ExceptionType.CLIENT_EXCEPTION;

public class ForbiddenException extends ApiException {
  public ForbiddenException(String message) {
    super(CLIENT_EXCEPTION, message);
  }
}
