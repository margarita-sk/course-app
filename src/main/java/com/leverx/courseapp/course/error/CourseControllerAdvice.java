package com.leverx.courseapp.course.error;

import com.leverx.courseapp.course.exception.CourseAlreadyExsistsException;
import com.leverx.courseapp.course.exception.NoSuchCourseException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CourseControllerAdvice {

    @ExceptionHandler(NoSuchCourseException.class)
    public ResponseEntity<Object> handleNoSuchCourseException(NoSuchCourseException ex) {
        var body = Map.of("timestamp", LocalDateTime.now(), "message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CourseAlreadyExsistsException.class)
    public ResponseEntity<Object> handleCourseAlreadyExsistsException(CourseAlreadyExsistsException ex) {
        return new ResponseEntity<>(Map.of("timestamp", LocalDateTime.now(), "message", ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Object> handleDateTimeParseException(DateTimeParseException ex){
        return new ResponseEntity<>(Map.of("exception", ex.getClass(), "timestamp", LocalDateTime.now(), "message", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
