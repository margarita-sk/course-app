package com.leverx.courseapp.tag.exception;

public class TagNotFoundException extends RuntimeException {

    public TagNotFoundException(int id) {
        super("Tag with Id " + id + " not found");
    }

    public TagNotFoundException() {
        super("Tag not found");
    }
}
