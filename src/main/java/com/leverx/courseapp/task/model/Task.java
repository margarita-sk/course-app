package com.leverx.courseapp.task.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TASKS")
public class Task {

  @Id
  @Min(0)
  @Column(name = "ID")
  private int id;

  @NotNull
  @Column(name = "NAME")
  private String name;

  @NotNull
  @Column(name = "DESCRIPTION")
  private String description;

  @NotNull
  @Column(name = "COURSE_ID")
  private int courseId;

  public Task(@NotNull String name, @NotNull String description, @NotNull int courseId) {
    this.name = name;
    this.description = description;
    this.courseId = courseId;
  }
}
