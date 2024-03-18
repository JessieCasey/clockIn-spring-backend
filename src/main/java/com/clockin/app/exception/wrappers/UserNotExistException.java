package com.clockin.app.exception.wrappers;

public class UserNotExistException extends RuntimeException {

  public UserNotExistException(String message) {
    super(message);
  }
}

