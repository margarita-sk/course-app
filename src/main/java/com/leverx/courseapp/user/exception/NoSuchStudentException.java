package com.leverx.courseapp.user.exception;

public class NoSuchStudentException extends RuntimeException {

    public NoSuchStudentException(int id) {
        super(String.format("Student with Id %d not found", id));
    }
}
