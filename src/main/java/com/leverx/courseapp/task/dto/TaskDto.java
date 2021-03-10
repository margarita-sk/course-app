package com.leverx.courseapp.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class TaskDto {

  @NotNull
  private String name;

  @NonNull
  private String description;
}
