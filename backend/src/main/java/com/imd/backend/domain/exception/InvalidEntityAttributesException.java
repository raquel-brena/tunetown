package com.imd.backend.domain.exception;

import java.util.Map;

public class InvalidEntityAttributesException extends RuntimeException {
  private final Map<String, String> errors;

  public InvalidEntityAttributesException(String message, Map<String, String> errors) {
    super(message);
    this.errors = errors;
  }

  public InvalidEntityAttributesException(String message, Throwable cause, Map<String, String> errors) {
    super(message, cause);
    this.errors = errors;
  } 

  public Map<String, String> getErrors() {
    return errors;
  }  
}
