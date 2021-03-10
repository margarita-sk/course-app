package com.leverx.courseapp.user.repository;

import com.leverx.courseapp.user.model.Student;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StudentRepository extends PagingAndSortingRepository<Student, String> {

    Student findStudentByEmail(String email);


}
