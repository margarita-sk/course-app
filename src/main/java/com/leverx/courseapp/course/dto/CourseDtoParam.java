package com.leverx.courseapp.course.dto;

import java.time.LocalDate;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Validated
@AllArgsConstructor
public class CourseDtoParam {

  @NotNull
  private String name;

  @NotNull
  private String description;

  @NotNull
  @DateTimeFormat
  private LocalDate startAssignmentDate;

  @NotNull
  @DateTimeFormat
  private LocalDate endAssignmentDate;

  @NotNull
  private Collection<String> tags;
}
