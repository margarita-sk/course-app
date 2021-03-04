package com.leverx.courseapp.course.exception;

public class CourseAlreadyExsistsException extends RuntimeException {

  public CourseAlreadyExsistsException(String name) {
    super("Course with name " +name + "  already exists");
  }
}
