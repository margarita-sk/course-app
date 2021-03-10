package com.leverx.courseapp.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class UserDtoParam {

    private static final String emailRegexp = "^.+@.+\\..+$";

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Pattern(message = "email is invalid", regexp = emailRegexp)
    private String email;

    @NotNull
    private char[] password;

}
