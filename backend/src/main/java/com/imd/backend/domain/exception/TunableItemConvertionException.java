package com.imd.backend.domain.exception;

public class TunableItemConvertionException extends RuntimeException{
  public TunableItemConvertionException(String message) {
    super(message);
  }

  public TunableItemConvertionException(String message, Throwable cause) {
    super(message, cause);
  }
}
