package com.leverx.courseapp.user.repository;

import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.user.model.Student;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

public interface StudentRepository extends PagingAndSortingRepository<Student, String> {

    Student findStudentByEmail(String email);


}
