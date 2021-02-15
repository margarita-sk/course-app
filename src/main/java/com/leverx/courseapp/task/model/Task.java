package com.leverx.courseapp.task.model;

import javax.persistence.*;
import lombok.*;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TASKS")
public class Task {

  @Id
  @Column(name = "ID")
  private int id;

  @NonNull
  @Column(name = "NAME")
  private String name;

  @NonNull
  @Column(name = "DESCRIPTION")
  private String description;

  @NonNull
  @Column(name = "COURSE_ID")
  private int courseId;

}
