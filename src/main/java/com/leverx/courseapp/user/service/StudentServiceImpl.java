package com.leverx.courseapp.user.service;

import com.leverx.courseapp.user.dto.StudentDto;
import com.leverx.courseapp.user.dto.StudentDtoParam;
import com.leverx.courseapp.user.model.Role;
import com.leverx.courseapp.user.model.Student;
import com.leverx.courseapp.user.repository.StudentRepository;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.leverx.courseapp.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public Collection<StudentDtoParam> receiveAll() {
        var students =
                StreamSupport.stream(studentRepository.findAll().spliterator(), false)
                        .map(student -> new StudentDtoParam(student.getId(), student.getName(), student.getFaculty()))
                        .collect(Collectors.toList());
        return students;
    }

    @Override
    public Student registerStudent(StudentDto studentDto) {
        var student = new Student(studentDto.getEmail(), studentDto.getName(), encoder.encode(studentDto.getPassword().toString()), Role.ROLE_STUDENT, studentDto.getFaculty());
        studentRepository.save(student);
        return student;
    }

    @Override
    public void deleteStudent(int id) {
        deleteStudent(id);
    }

    @Override
    public Student findStudentById(int id) {
        var student = studentRepository.findById(id);
        return student.orElseThrow(RuntimeException::new);
    }


}
