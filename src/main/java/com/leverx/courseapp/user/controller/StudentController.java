package com.leverx.courseapp.user.controller;

import com.leverx.courseapp.user.dto.StudentDto;
import com.leverx.courseapp.user.dto.StudentDtoParam;
import com.leverx.courseapp.user.model.Student;
import com.leverx.courseapp.user.service.StudentService;
import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/students")
public class StudentController {

  private final StudentService studentService;



  @GetMapping("")
  public Collection<StudentDtoParam> receiveStudents() {
    var students = studentService.receiveAll();
    return students;
  }

  @GetMapping("/{id}")
  public Student receiveStudentById(@PathVariable int id) {
    var student = studentService.findStudentById(id);
    return student;
  }

  @PostMapping("")
  public String registerStudent(@RequestBody @Valid StudentDto studentDto) {
    studentService.registerStudent(studentDto);
    return "OK";
  }

  @DeleteMapping("/{id}")
  public void deleteStudent(@PathVariable int id) {
    studentService.deleteStudent(id);
  }
}
