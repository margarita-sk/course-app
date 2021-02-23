package com.leverx.courseapp.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentDtoRegistration {

    private String firstName;

    private String lastName;

    private String email;

    private String faculty;

    private char[] password;

}
