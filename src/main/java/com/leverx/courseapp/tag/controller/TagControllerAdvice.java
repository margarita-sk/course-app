package com.leverx.courseapp.tag.controller;

import com.leverx.courseapp.course.exception.NoSuchCourseException;
import com.leverx.courseapp.tag.exception.TagNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class TagControllerAdvice {

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<Object> handleTagNotFoundException(
            TagNotFoundException ex) {
        var body = Map.of("timestamp", LocalDateTime.now(), "message", ex);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(
            AccessDeniedException ex) {
        return new ResponseEntity<>(Map.of("message", ex), HttpStatus.UNAUTHORIZED);
    }
}
