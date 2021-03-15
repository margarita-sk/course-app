package com.leverx.courseapp.tag.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TAGS")
public class Tag {

  @Id
  @Column(name = "ID")
  private int id;

  @Column(name = "NAME")
  private String name;
}
