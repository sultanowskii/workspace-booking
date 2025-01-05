package org.wb.components.error.exception;

public class InvalidParamsException extends RuntimeException {
    public InvalidParamsException(String message) {
        super("Request param is invalid:" + message);
    }
}
