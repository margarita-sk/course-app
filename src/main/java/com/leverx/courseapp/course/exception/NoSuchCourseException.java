package com.leverx.courseapp.course.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoSuchCourseException extends RuntimeException {

  public NoSuchCourseException(int id) {
    super("Course with Id " + id + "  not found");
  }
}
