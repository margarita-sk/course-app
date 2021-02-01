package com.leverx.courseapp.course.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;


@Entity
@Data
@Table(name = "COURSES")
public class Course {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "START_ASSIGNMENT_DATE")
    private LocalDate startAssignmentDate;

    @Column(name = "END_ASSIGNMENT_DATE")
    private LocalDate endAssignmentDate;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Collection<Task> tasks;

    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<Tag> tags;



}
