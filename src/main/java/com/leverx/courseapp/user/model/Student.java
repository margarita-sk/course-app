package com.leverx.courseapp.user.model;

import com.leverx.courseapp.course.model.Course;
import java.util.Collection;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@NoArgsConstructor
@Data
@Table(name = "STUDENTS")
@PrimaryKeyJoinColumn(name = "STUDENT_ID")
public class Student extends User {

  @NonNull
  @Column(name = "FACULTY")
  private String faculty;

  @ManyToMany(mappedBy = "students")
  private Collection<Course> courses;


  public Student(String email, String name, String password, Role role, String faculty) {
    super(email, name, password, role);
    this.faculty = faculty;
  }
}
