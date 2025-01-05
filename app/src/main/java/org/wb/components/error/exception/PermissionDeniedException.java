package org.wb.components.error.exception;

public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException(String message) {
        super("Permission denied: " + message);
    }
}
