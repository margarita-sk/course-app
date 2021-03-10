package com.leverx.courseapp.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;

@Data
@AllArgsConstructor
public class CourseDtoResponseFull {

  private String name;

  private String description;

  private LocalDate startAssignmentDate;

  private LocalDate endAssignmentDate;

  private Collection<String> tags;
}
