package com.leverx.courseapp.user.dto;

import lombok.Getter;

@Getter
public class StudentDtoParam extends UserDtoParam{

  private String faculty;

  public StudentDtoParam(String firstName, String lastName, String email, char[] password, String faculty) {
    super(firstName, lastName, email, password);
    this.faculty = faculty;
  }
}
