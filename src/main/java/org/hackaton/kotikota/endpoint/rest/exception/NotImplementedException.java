package org.hackaton.kotikota.endpoint.rest.exception;

import static org.hackaton.kotikota.endpoint.rest.exception.ApiException.ExceptionType.SERVER_EXCEPTION;

public class NotImplementedException extends ApiException {
  public NotImplementedException(String message) {
    super(SERVER_EXCEPTION, message);
  }
}
