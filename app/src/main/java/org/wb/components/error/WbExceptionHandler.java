package org.wb.components.error;

import jakarta.validation.ConstraintViolationException;

import org.postgresql.util.PSQLException;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.wb.components.error.exception.InvalidBodyException;
import org.wb.components.error.exception.InvalidParamsException;
import org.wb.components.error.exception.NotFoundException;
import org.wb.components.error.exception.PermissionDeniedException;
import org.wb.db.DBError;
import org.wb.gen.model.Error;

@ControllerAdvice
public class WbExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAnyException(Exception ex) {
        final Error Error = new Error().message("Unexpected exception:" + ex).description(ex.getLocalizedMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(Error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request) {
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final Error Error = new Error().message(ex.getLocalizedMessage()).description(errors.toString());
        return handleExceptionInternal(ex, Error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<Object> handleAuthenticationException(Exception ex) {
        final Error Error = new Error().message("Authentication failed").description(ex.getLocalizedMessage());
        return new ResponseEntity<>(Error, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex) {
        final Error Error = new Error()
                .message("You don't have permissions to perform this action")
                .description(ex.getLocalizedMessage());
        return new ResponseEntity<>(Error, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<Object> handleNotFoundException(Exception ex) {
        final Error Error = new Error()
                .message("Resource not found")
                .description(ex.getLocalizedMessage());
        return new ResponseEntity<>(Error, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ PermissionDeniedException.class })
    public ResponseEntity<Object> handlePermissionDenied(Exception ex) {
        final Error Error = new Error()
                .message("You don't have permissions to perform this action")
                .description(ex.getLocalizedMessage());
        return new ResponseEntity<>(Error, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolationException(Exception ex) {
        final Error Error = new Error()
                .message("Invalid request")
                .description(ex.getLocalizedMessage());
        return new ResponseEntity<>(Error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ InvalidBodyException.class })
    public ResponseEntity<Object> handleInvalidBodyException(Exception ex) {
        final Error Error = new Error()
                .message("Invalid request (body)")
                .description(ex.getLocalizedMessage());
        return new ResponseEntity<>(Error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ InvalidParamsException.class })
    public ResponseEntity<Object> handleInvalidParamsException(Exception ex) {
        final Error Error = new Error()
                .message("Invalid request (params)")
                .description(ex.getLocalizedMessage());
        return new ResponseEntity<>(Error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ PSQLException.class })
    public ResponseEntity<Object> handlePSQLException(PSQLException ex) {
        switch (DBError.valueFrom(ex.getSQLState())) {
            case DBError.VISUAL_OBJECTS_COLLIDE:
                return handleInvalidBodyException(ex);
            case DBError.WORKPLACE_BOOKING_DATE_OUTSIDE_RANGE:
                return handleInvalidBodyException(ex);
            case DBError.MEETING_ROOM_BOOKING_DATE_OUTSIDE_RANGE:
                return handleInvalidBodyException(ex);
            default:
                break;
        }
        throw new InternalError(ex);
    }

    @ExceptionHandler({ JpaSystemException.class })
    public ResponseEntity<Object> handleJpaSystemException(JpaSystemException ex) {
        var cause = ex.getMostSpecificCause();
        if (cause instanceof PSQLException) {
            var postgresException = (PSQLException) cause;
            return handlePSQLException(postgresException);
        }
        throw ex;
    }
}