package com.leverx.courseapp.task.controller;

import com.leverx.courseapp.course.exception.NoSuchCourseException;
import com.leverx.courseapp.task.exception.NoSuchTaskException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class TaskControllerAdvice {

    @ExceptionHandler(NoSuchTaskException.class)
    public ResponseEntity<Object> handleNoSuchTaskException(
            NoSuchTaskException ex) {
        var body = Map.of("timestamp", LocalDateTime.now(), "message", ex);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(
            AccessDeniedException ex) {
        return new ResponseEntity<>(Map.of("message", ex), HttpStatus.UNAUTHORIZED);
    }
}
