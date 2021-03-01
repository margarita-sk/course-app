package com.leverx.courseapp.course.exception;

import lombok.NoArgsConstructor;

public class CannotDeleteCourseException extends RuntimeException {

  public CannotDeleteCourseException() {
    super("Cannot delete the course. It doesn't exist");
  }
}
