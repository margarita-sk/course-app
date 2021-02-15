package com.leverx.courseapp.tag.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leverx.courseapp.course.model.Course;
import java.util.Collection;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "TAGS")
public class Tag {

  @Id
  @Column(name = "ID")
  private int id;

  @Column(name = "NAME")
  private String name;

}
