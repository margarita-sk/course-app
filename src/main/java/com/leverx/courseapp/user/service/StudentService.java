package com.leverx.courseapp.user.service;

import com.leverx.courseapp.user.dto.StudentDto;
import com.leverx.courseapp.user.dto.StudentDtoShort;
import com.leverx.courseapp.user.model.Student;

import java.util.Collection;

import org.springframework.security.access.prepost.PreAuthorize;

public interface StudentService {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Collection<StudentDtoShort> receiveAll();

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteStudent(int id);

//    @PreAuthorize("hasRole('ROLE_ADMIN) or #authUser.id == #id")
    Student findStudentById(int id);

    Student registerStudent(StudentDto studentDto);
}
