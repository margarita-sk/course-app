package com.leverx.courseapp.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leverx.courseapp.course.model.Course;
import java.util.Collection;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class StudentOktaDto {

  @NonNull private String email;

  @NonNull private String firstName;

  @NonNull private String lastName;

  private String faculty;

  @JsonIgnore private Collection<Course> courses;
}
