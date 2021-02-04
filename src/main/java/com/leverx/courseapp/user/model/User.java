package com.leverx.courseapp.user.model;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "USERS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class User {

  @Id
  @Column(name = "ID")
  private int id;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "NAME")
  private String name;

  @Column(name = "PASSWORD")
  private char[] password;
}
