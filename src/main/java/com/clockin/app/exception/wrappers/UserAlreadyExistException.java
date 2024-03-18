package com.clockin.app.exception.wrappers;

import lombok.Getter;

@Getter
public class UserAlreadyExistException extends RuntimeException {

    public final String REASON = "User already exists";

    public UserAlreadyExistException(String message) {
        super(message);
    }

}

