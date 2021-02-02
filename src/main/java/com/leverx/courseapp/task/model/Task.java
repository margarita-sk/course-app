package com.leverx.courseapp.task.model;

import com.leverx.courseapp.course.model.Course;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@Table(name = "TASKS")
public class Task {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "STATUS")
    private String status;

    @ToString.Exclude
    @ManyToOne (targetEntity = Course.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_COURSE")
    private Course course;

    public enum Status {
        DONE, IN_PROCESS, NOT_STARTED
    }
}
