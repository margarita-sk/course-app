package com.leverx.courseapp.course.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table (name = "TAGS")
public class Tag {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;


}
