package com.leverx.courseapp.user.controller;

import com.leverx.courseapp.user.dto.StudentDto;
import com.leverx.courseapp.user.model.Student;
import com.leverx.courseapp.user.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {

    private final StudentService studentService;

    @PostMapping("users/auth")
    public void authenticate(@ModelAttribute StudentDto studentDto) {
    }

    @PostMapping("/students/registration")
    public Student registerStudent(@ModelAttribute StudentDto studentDto) {
        var student = studentService.registerStudent(studentDto);
        return student;
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
    }

    @PutMapping("/students/{id}")
    public Student editStudent(@PathVariable int id, @ModelAttribute StudentDto studentDto){
        return null;
    }

}
