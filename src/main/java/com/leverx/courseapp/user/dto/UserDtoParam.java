package com.leverx.courseapp.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDtoParam {

    private String firstName;

    private String lastName;

    private String email;

    private char[] password;

}
