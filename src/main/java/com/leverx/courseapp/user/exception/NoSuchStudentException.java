package com.leverx.courseapp.user.exception;

public class NoSuchStudentException extends RuntimeException {

  public NoSuchStudentException(String name) {
    super("Student with credentials: " + name + "not found");
  }

  public NoSuchStudentException() {
    super("Student not found");
  }
}
