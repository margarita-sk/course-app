package com.leverx.courseapp.user.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoSuchStudentException extends RuntimeException {

  public NoSuchStudentException(String name) {
    super("Student with credentials: " + name + "not found");
  }
}
