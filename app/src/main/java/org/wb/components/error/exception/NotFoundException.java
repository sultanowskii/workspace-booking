package org.wb.components.error.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super("Resource not found: " + message);
    }
}
