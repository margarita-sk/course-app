package com.leverx.courseapp.course.model;

import com.leverx.courseapp.tag.model.Tag;
import com.leverx.courseapp.task.model.Task;
import com.leverx.courseapp.user.model.Student;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = {"students", "tags", "tasks"})
@Table(name = "COURSES")
public class Course {

    @Id
    @Min(0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "DESCRIPTION")
    private String description;

    @DateTimeFormat
    @Column(name = "START_ASSIGNMENT_DATE")
    private LocalDate startAssignmentDate;

    @DateTimeFormat
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


    public Course( @NotNull String name,  @NotNull String description, LocalDate startAssignmentDate, LocalDate endAssignmentDate) {
        this.name = name;
        this.description = description;
        this.startAssignmentDate = startAssignmentDate;
        this.endAssignmentDate = endAssignmentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return name.equals(course.name) && description.equals(course.description) && startAssignmentDate.equals(course.startAssignmentDate) && endAssignmentDate.equals(course.endAssignmentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, startAssignmentDate, endAssignmentDate);
    }
}
