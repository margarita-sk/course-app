package com.leverx.courseapp.user.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StudentDto {

  @NotNull
  private String email;

  @NotNull
  private String name;

  @NotNull
  private char[] password;

  @NotNull
  private String faculty;
}
