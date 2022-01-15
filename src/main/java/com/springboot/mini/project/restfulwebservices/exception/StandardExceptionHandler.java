package com.springboot.mini.project.restfulwebservices.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
public class StandardExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex,
                                                           WebRequest request) {
        StandardExceptionResponse standardExceptionResponse =
                new StandardExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(standardExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleAllException(UserNotFoundException ex,
                                                           WebRequest request) {
        StandardExceptionResponse standardExceptionResponse =
                new StandardExceptionResponse(new Date(), ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity<>(standardExceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        StandardExceptionResponse standardExceptionResponse =
                new StandardExceptionResponse(new Date(), "Validation Failed",
                        ex.getBindingResult().toString());
        return new ResponseEntity(standardExceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
