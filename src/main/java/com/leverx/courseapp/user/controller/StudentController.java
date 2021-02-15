package com.leverx.courseapp.user.controller;

import com.leverx.courseapp.user.dto.StudentDto;
import com.leverx.courseapp.user.dto.StudentDtoShort;
import com.leverx.courseapp.user.model.Student;
import com.leverx.courseapp.user.service.StudentService;
import java.util.Collection;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Api(value = "students")
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@RequestMapping(path = "/students", produces = "application/json")
public class StudentController {

  private final StudentService studentService;

  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @GetMapping
  public Collection<StudentDtoShort> receiveStudents() {
    var students = studentService.receiveAll();
    return students;
  }

  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @GetMapping("/{id}")
  public Student receiveStudentById(@PathVariable int id) {
    var student = studentService.findStudentById(id);
    return student;
  }

  @ApiResponses(value = {
          @ApiResponse(code = 201, message = "Student is created successfully")
  })
  @ResponseStatus(code = HttpStatus.ACCEPTED)
  @PostMapping
  public void registerStudent(@RequestBody @Valid StudentDto studentDto) {
    studentService.registerStudent(studentDto);
  }

  @ApiOperation(value = "delete student")
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Student was deleted successfully"),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @ResponseStatus(code = HttpStatus.OK)
  @DeleteMapping("/{id}")
  public void deleteStudent(@PathVariable int id) {
    studentService.deleteStudent(id);
  }
}
