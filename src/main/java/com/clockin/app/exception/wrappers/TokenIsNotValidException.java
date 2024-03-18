package com.clockin.app.exception.wrappers;

public class TokenIsNotValidException extends RuntimeException {

    public final String REASON = "Token is not valid";

    public TokenIsNotValidException(String message) {
        super(message);
    }
}
