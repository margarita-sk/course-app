package com.leverx.courseapp.task.error;

import com.leverx.courseapp.task.exception.NoSuchTaskException;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TaskControllerAdvice {

  @ExceptionHandler(NoSuchTaskException.class)
  public ResponseEntity<Object> handleNoSuchTaskException(NoSuchTaskException ex) {
    var body = Map.of("timestamp", LocalDateTime.now(), "message", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }
}
