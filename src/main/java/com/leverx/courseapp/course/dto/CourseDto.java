package com.leverx.courseapp.course.dto;

import com.leverx.courseapp.tag.model.Tag;
import java.time.LocalDate;
import java.util.Collection;
import lombok.Data;

@Data
public class CourseDto {

  private String name;
  private String description;
  private LocalDate startAssignmentDate;
  private LocalDate endAssignmentDate;

  private Collection<Tag> tags;
}
