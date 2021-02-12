package com.leverx.courseapp.user.model;

import javax.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@RequiredArgsConstructor
@Entity
@Table(name ="USERS")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

  @Id
  @Column(name = "ID")
  private int id;

  @NonNull
  @Column(name = "EMAIL")
  private String email;

  @NonNull
  @Column(name = "NAME")
  private String name;

  @NonNull
  @Column(name = "PASSWORD")
  private String password;

  @NonNull
  @Enumerated(EnumType.STRING)
  @Column(name = "ROLE")
  private Role role;

}
