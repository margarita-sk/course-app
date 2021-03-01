package com.leverx.courseapp.tag.model;

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
  private Long id;

  @Column(name = "NAME")
  private String name;
}
