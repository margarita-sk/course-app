package com.leverx.courseapp.tag.model;

import com.leverx.courseapp.course.model.Course;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table (name = "TAGS")
public class Tag {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "COURSE_TAG",
            joinColumns = @JoinColumn(name = "ID_TAG"),
            inverseJoinColumns = @JoinColumn(name = "ID_COURSE"))
    private Collection<Course> courses;


}
