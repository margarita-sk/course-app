package com.leverx.courseapp.course.model;

import com.leverx.courseapp.tag.model.Tag;
import com.leverx.courseapp.task.model.Task;
import com.leverx.courseapp.user.model.Student;
import java.time.LocalDate;
import java.util.Collection;
import javax.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "COURSES")
@ToString(exclude = "students")
public class Course {

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
  @Column(name = "START_ASSIGNMENT_DATE")
  private LocalDate startAssignmentDate;

  @NonNull
  @Column(name = "END_ASSIGNMENT_DATE")
  private LocalDate endAssignmentDate;

  @OneToMany(mappedBy = "courseId", cascade = CascadeType.REMOVE)
  private Collection<Task> tasks;

  @ManyToMany
  @JoinTable(
      name = "COURSE_TAG",
      joinColumns = @JoinColumn(name = "COURSE_ID"),
      inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
  private Collection<Tag> tags;

  @ManyToMany
  @JoinTable(
      name = "STUDENT_COURSE_ASSIGNMENT",
      joinColumns = @JoinColumn(name = "COURSE_ID"),
      inverseJoinColumns = @JoinColumn(name = "STUDENT_ID"))
  private Collection<Student> students;
}
