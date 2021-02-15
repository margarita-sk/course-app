package com.leverx.courseapp.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentDtoShort {

  private int id;

  private String name;

  private String faculty;
}
