package com.leverx.courseapp.user.dto;

import lombok.Data;

@Data
public class StudentDto {


    private String email;

    private String name;

    private char[] password;

    private String faculty;
}
