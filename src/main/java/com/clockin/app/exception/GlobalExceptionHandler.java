package com.clockin.app.exception;

import com.clockin.app.exception.wrappers.TokenIsNotValidException;
import com.clockin.app.exception.wrappers.UserAlreadyExistException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TokenIsNotValidException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleUserAlreadyExistException(HttpServletRequest request, TokenIsNotValidException ex) {
        return ErrorResponse.builder()
                .errorCode(HttpStatus.CONFLICT.toString())
                .errorReason(ex.REASON)
                .errorDetails(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(getRequestPath(request))
                .build();
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleUserAlreadyExistException(HttpServletRequest request, UserAlreadyExistException ex) {
        return ErrorResponse.builder()
                .errorCode(HttpStatus.CONFLICT.toString())
                .errorReason(ex.REASON)
                .errorDetails(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(getRequestPath(request))
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleException(HttpServletRequest request, Exception ex) {
        return ErrorResponse.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .errorDetails(ex.getMessage()) // Set the exception message as error details
                .timestamp(LocalDateTime.now()) // Set the current timestamp
                .path(getRequestPath(request)) // Set the request path
                .build();
    }

    private String getRequestPath(HttpServletRequest request) {
        if (request != null) {
            return request.getRequestURI();
        }
        return null;
    }
}
