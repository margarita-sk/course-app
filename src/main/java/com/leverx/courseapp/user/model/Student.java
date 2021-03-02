package com.leverx.courseapp.user.model;

import com.leverx.courseapp.course.model.Course;

import java.util.Collection;
import javax.persistence.*;

import lombok.*;
import org.hibernate.id.IncrementGenerator;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "STUDENTS")
public class Student {

    @Id
    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

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
