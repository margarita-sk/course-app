package com.leverx.courseapp.user.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ADMINS")
@PrimaryKeyJoinColumn(name = "ADMIN_ID")
public class Admin extends User {

  @Column(name = "EXPERATION_DATE")
  private LocalDate experationDate;
}
