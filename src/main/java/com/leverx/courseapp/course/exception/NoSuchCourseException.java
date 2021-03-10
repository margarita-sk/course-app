package com.leverx.courseapp.course.exception;

public class NoSuchCourseException extends RuntimeException {

    public NoSuchCourseException(int id) {
        super("Course with Id " + id + "  not found");
    }

    public NoSuchCourseException() {
        super("This course was not found");
    }
}
