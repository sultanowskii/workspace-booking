package org.wb.components.error.exception;

public class InvalidBodyException extends RuntimeException {
    public InvalidBodyException(String message) {
        super("Request body is invalid: " + message);
    }
}