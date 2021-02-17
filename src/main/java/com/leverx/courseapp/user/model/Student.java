package com.leverx.courseapp.user.model;

import com.leverx.courseapp.course.model.Course;
import java.util.Collection;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "STUDENTS")
public class Student {

  @Id
  @Column(name = "ID")
  private String id;

  @Column(name = "NAME")
  private String name;

  @NonNull
  @Column(name = "FACULTY")
  private String faculty;

  @ManyToMany(mappedBy = "students")
  private Collection<Course> courses;

}
