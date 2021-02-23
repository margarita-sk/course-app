package com.leverx.courseapp.user.repository;

import com.leverx.courseapp.user.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends JpaRepository<Student, String> {}
