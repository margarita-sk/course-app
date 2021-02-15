package com.leverx.courseapp.tag.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TagNotFoundException extends RuntimeException {

    public TagNotFoundException(int id){
        super(String.format("Tag with Id %d not found", id));
    }

}
