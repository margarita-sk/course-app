package com.leverx.courseapp.course.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "TASKS")
public class Task {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;
}
