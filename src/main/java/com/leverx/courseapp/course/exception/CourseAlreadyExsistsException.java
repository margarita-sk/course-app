package com.leverx.courseapp.course.exception;

import com.leverx.courseapp.course.dto.CourseDtoShort;

public class CourseAlreadyExsistsException extends RuntimeException {

  public CourseAlreadyExsistsException(String name) {
    super("Course with name " +name + "  already exists");
  }
}
