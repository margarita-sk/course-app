package com.leverx.courseapp.user.repository;

import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.user.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface StudentRepository extends CrudRepository<Student, Integer> {

    Student findByNameContains(String name);
}
