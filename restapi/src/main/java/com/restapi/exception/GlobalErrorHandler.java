package com.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<Object> handleInvalidDataException(
            RuntimeException ex, WebRequest request) {
    	
        return new ResponseEntity<>("hiiii", HttpStatus.BAD_REQUEST);
    }

}
