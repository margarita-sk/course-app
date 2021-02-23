package com.leverx.courseapp.user.model;

import com.leverx.courseapp.course.model.Course;
import java.util.Collection;
import javax.persistence.*;
import lombok.*;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "STUDENTS")
public class Student {

  @Id
  @NonNull
  @Column(name = "EMAIL")
  private String email;

  @NonNull
  @Column(name = "FACULTY")
  private String faculty;

  @ManyToMany(mappedBy = "students", cascade = CascadeType.ALL)
  private Collection<Course> courses;
}
