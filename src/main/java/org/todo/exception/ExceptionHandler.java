package org.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.todo.model.response.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class ExceptionHandler {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss a";

    @org.springframework.web.bind.annotation.ExceptionHandler(TodoException.class)
    public ResponseEntity<?> GenericException(TodoException exception, WebRequest webRequest) {
        return createResponseEntity(exception, exception.getStatusCode(), webRequest);
    }


    private ResponseEntity<?> createResponseEntity(Exception exception, HttpStatus status, WebRequest webRequest) {
        ExceptionModel errorDetails = new ExceptionModel(
                status.value(),
                exception.getMessage(),
                DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(LocalDateTime.now()),
                webRequest.getDescription(false)
        );
        Response errorResponse = Response.builder()
                .success(false)
                .error(errorDetails)
                .build();
        return new ResponseEntity<>(errorResponse, status);
    }
}
