package com.leverx.courseapp.course.controller;

import com.leverx.courseapp.course.exception.NoSuchCourseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CourseControllerAdvice {

    @ExceptionHandler(NoSuchCourseException.class)
    public ResponseEntity<Object> handleNoSuchCourseException(
            NoSuchCourseException ex) {
        var body = Map.of("timestamp", LocalDateTime.now(), "message", ex);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(
            AccessDeniedException ex) {
        return new ResponseEntity<>(Map.of("message", ex), HttpStatus.UNAUTHORIZED);
    }
}
