package com.leverx.courseapp.course.exception;

public class NoSuchCourseException extends RuntimeException {

    public NoSuchCourseException(int id){
        super(String.format("Course with Id %d not found", id));
    }
}
