package com.leverx.courseapp.user.repository;

import com.leverx.courseapp.user.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {

    Student findByNameContains(String studentName);
}
