package com.leverx.courseapp.user.controller;

import com.leverx.courseapp.user.exception.NoSuchStudentException;

import java.time.LocalDateTime;
import java.util.Map;

import com.leverx.courseapp.user.exception.StudentRegistrationException;
import com.okta.sdk.resource.ResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StudentControllerAdvice {

    @ExceptionHandler(NoSuchStudentException.class)
    public ResponseEntity<Object> handleNoSuchCourseStudent(NoSuchStudentException ex) {
        var body = Map.of("timestamp", LocalDateTime.now(), "message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>(
                Map.of("message", ex.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(StudentRegistrationException.class)
    public ResponseEntity<Object> handleStudentRegistrationException(StudentRegistrationException ex) {
        return new ResponseEntity<>(
                Map.of("timestamp", LocalDateTime.now(), "message", ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity<Object> handleResourceExceptionException(ResourceException ex) {
        return new ResponseEntity<>(
                Map.of("timestamp", LocalDateTime.now(), "message", ex.getMessage()), HttpStatus.CONFLICT);
    }
}
