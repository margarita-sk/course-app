package com.leverx.courseapp.course.model;

import com.leverx.courseapp.tag.model.Tag;
import com.leverx.courseapp.task.model.Task;
import lombok.Data;
import lombok.ToString;

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

    @OneToMany(targetEntity = Task.class, mappedBy = "course", fetch = FetchType.LAZY)
    private Collection<Task> tasks;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "COURSE_TAG",
            joinColumns = @JoinColumn(name = "ID_COURSE"),
            inverseJoinColumns = @JoinColumn(name = "ID_TAG"))
    private Collection<Tag> tags;



}
