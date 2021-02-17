package com.leverx.courseapp.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class StudentDto {

  @NonNull
  private String name;

  @NonNull
  private String faculty;

  private String email;
}
