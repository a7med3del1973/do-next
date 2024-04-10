package org.todo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TodoException extends RuntimeException {

    private final HttpStatus statusCode;

    public TodoException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
