package com.leverx.courseapp.user.exception;

public class StudentRegistrationException extends RuntimeException {

    public StudentRegistrationException() {
        super("Cannot register a student");
    }
}
