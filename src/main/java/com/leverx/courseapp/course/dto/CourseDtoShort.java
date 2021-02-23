package com.leverx.courseapp.course.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.Collection;

@Data
public class CourseDtoShort {

  @NonNull private String name;

  @NonNull private String description;
}
