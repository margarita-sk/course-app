package com.leverx.courseapp.user.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    private int id;

    private String email;

    private String name;


}
