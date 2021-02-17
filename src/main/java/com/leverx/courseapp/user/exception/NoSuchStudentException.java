package com.leverx.courseapp.user.exception;

public class NoSuchStudentException extends RuntimeException {

    public NoSuchStudentException(String name) {
        super(String.format("Student with name %d not found", name));
    }
}
