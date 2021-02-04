package com.leverx.courseapp.user.service;

import com.leverx.courseapp.user.dto.StudentDto;
import com.leverx.courseapp.user.model.Student;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {

    Student findStudentByName(String name);

    Student registerStudent(StudentDto studentDto);

    void deleteStudent(int id);

    Student editStudent(int id, StudentDto studentDto);
}
