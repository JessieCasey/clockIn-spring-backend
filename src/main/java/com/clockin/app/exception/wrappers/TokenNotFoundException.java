package com.clockin.app.exception.wrappers;

public class TokenNotFoundException extends RuntimeException {

  public TokenNotFoundException(String message) {
    super(message);
  }
}

