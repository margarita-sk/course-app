package com.leverx.courseapp.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leverx.courseapp.course.model.Course;
import java.util.Collection;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "STUDENTS")
public class Student extends User {

  @Column(name = "FACULTY")
  private String faculty;

  @JsonIgnore
  @ManyToMany(mappedBy = "students")
  private Collection<Course> courses;
}
