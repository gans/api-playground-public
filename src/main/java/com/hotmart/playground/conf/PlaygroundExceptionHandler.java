package com.hotmart.playground.conf;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class PlaygroundExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        if (ex.getMostSpecificCause().getMessage().toLowerCase().contains("unique")) {
            return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.CONFLICT, request);
        } else {
            return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
        }
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundExceptionException(EntityNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
