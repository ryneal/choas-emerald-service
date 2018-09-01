package com.example.chaosemeraldservice.exception.handler;

import com.example.chaosemeraldservice.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EmeraldCreationFailedException.class})
    protected ResponseEntity<Object> handleCreationFailedException(EmeraldCreationFailedException ex, WebRequest request) {
        return handleExceptionInternal(ex, "An internal error occurred, Emerald was not created",
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {EmeraldDeletionFailedException.class})
    protected ResponseEntity<Object> handleDeletionFailedException(EmeraldDeletionFailedException ex, WebRequest request) {
        return handleExceptionInternal(ex, "An internal error occurred, Emerald was not deleted",
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {EmeraldNotFoundException.class})
    protected ResponseEntity<Object> handleEmeraldNotFoundException(EmeraldNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, "Requested Emerald does not exist",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {EmeraldNotUpdatedException.class})
    protected ResponseEntity<Object> handleEmeraldNotUpdatedException(EmeraldNotUpdatedException ex, WebRequest request) {
        return handleExceptionInternal(ex, "Requested Emerald was not updated",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {EmeraldUpdateFailedException.class})
    protected ResponseEntity<Object> handleEmeraldUpdateFailedException(EmeraldDeletionFailedException ex, WebRequest request) {
        return handleExceptionInternal(ex, "An internal error occurred, Emerald was not updated",
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "An internal error occurred",
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
