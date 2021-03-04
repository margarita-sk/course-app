package com.leverx.courseapp.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class CourseDtoResponse {

  private int id;

  private String name;

  private String description;
}
