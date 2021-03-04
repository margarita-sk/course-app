package com.leverx.courseapp.error;

import com.leverx.courseapp.user.exception.NoSuchStudentException;
import com.leverx.courseapp.user.exception.StudentRegistrationException;
import com.okta.sdk.resource.ResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GeneralControllerAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        var body = Map.of("timestamp", LocalDateTime.now(), "message", ex.getLocalizedMessage());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity<Object> handleResourceExceptionException(ResourceException ex) {
        return new ResponseEntity<>(
                Map.of("timestamp", LocalDateTime.now(), "message", ex.getMessage()), HttpStatus.CONFLICT);
    }
}
