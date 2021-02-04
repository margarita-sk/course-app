package com.leverx.courseapp.user.service;

import com.leverx.courseapp.user.dto.StudentDto;
import com.leverx.courseapp.user.model.Student;
import com.leverx.courseapp.user.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Student findStudentByName(String name) {
        var student = studentRepository.findByNameContains(name);
        return student;
    }

    @Override
    public Student registerStudent(StudentDto studentDto) {
        return null;
    }

    @Override
    public void deleteStudent(int id) {
        deleteStudent(id);
    }

    @Override
    public Student editStudent(int id, StudentDto studentDto) {
        return null;
    }
}
