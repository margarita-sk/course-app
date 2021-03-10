package com.leverx.courseapp.user.model;

import com.leverx.courseapp.course.model.Course;

import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.leverx.courseapp.validator.Sorting;
import lombok.*;
import org.hibernate.id.IncrementGenerator;
import org.springframework.validation.annotation.Validated;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Table(name = "STUDENTS")
public class Student {

    @Id
    @Column(name = "EMAIL", unique = true)
    private String email;

    @NotNull
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotNull
    @Column(name = "LAST_NAME")
    private String lastName;

    @NotNull
    @Column(name = "FACULTY")
    private String faculty;

    @ManyToMany(mappedBy = "students", cascade = CascadeType.ALL)
    private Collection<Course> courses;

    public Student(String email, String firstName, String lastName, String faculty) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.faculty = faculty;
    }
}
